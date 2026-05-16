package com.example.lovefasilitas.ui.booking

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lovefasilitas.LoveFasilitasApp
import com.example.lovefasilitas.R
import com.example.lovefasilitas.domain.common.toRupiah
import com.example.lovefasilitas.domain.common.toRupiahPerTrip
import com.example.lovefasilitas.domain.model.Facility
import com.example.lovefasilitas.domain.model.OrderHistory
import com.example.lovefasilitas.domain.usecase.AddOrderUseCase
import com.google.android.material.snackbar.Snackbar

class BookingConfirmationFragment : Fragment(R.layout.fragment_booking_confirmation) {

    private var facility: Facility? = null
    private var selectedDate: String = ""
    private var selectedDuration: Int = 0

    private val bookingViewModel: BookingViewModel by lazy {
        val app = requireActivity().application as LoveFasilitasApp
        val addOrderUseCase = AddOrderUseCase(app.container.orderHistoryRepository)
        ViewModelProvider(this, BookingViewModelFactory(addOrderUseCase))[BookingViewModel::class.java]
    }

    companion object {
        private const val ARG_ID = "id"
        private const val ARG_NAME = "name"
        private const val ARG_PRICE = "price"
        private const val ARG_DESC = "description"
        private const val ARG_IMAGE = "imageResId"
        private const val ARG_CAPACITY = "capacity"
        private const val ARG_LOCATION = "location"
        private const val ARG_DATE = "date"
        private const val ARG_DURATION = "duration"

        fun newInstance(facility: Facility, date: String, duration: Int): BookingConfirmationFragment {
            return BookingConfirmationFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, facility.id)
                    putString(ARG_NAME, facility.name)
                    putInt(ARG_PRICE, facility.price)
                    putString(ARG_DESC, facility.description)
                    putInt(ARG_IMAGE, facility.imageResId)
                    putInt(ARG_CAPACITY, facility.capacity)
                    putString(ARG_LOCATION, facility.location)
                    putString(ARG_DATE, date)
                    putInt(ARG_DURATION, duration)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        if (args == null || !args.containsKey(ARG_ID)) {
            Snackbar.make(view, "Failed to load booking details", Snackbar.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
            return
        }

        facility = Facility(
            id = args.getInt(ARG_ID),
            name = args.getString(ARG_NAME) ?: "",
            price = args.getInt(ARG_PRICE),
            imageResId = args.getInt(ARG_IMAGE),
            description = args.getString(ARG_DESC) ?: "",
            capacity = args.getInt(ARG_CAPACITY),
            location = args.getString(ARG_LOCATION) ?: ""
        )
        selectedDate = args.getString(ARG_DATE) ?: ""
        selectedDuration = args.getInt(ARG_DURATION)

        facility?.let { setupUI(it, view) }
        setupButtons(view)
        observeViewModel()
        updatePricing()
    }

    private fun setupUI(facility: Facility, view: View) {
        view.findViewById<ImageView>(R.id.ivFacilityImage)?.setImageResource(facility.imageResId)
        view.findViewById<TextView>(R.id.tvFacilityName)?.text = facility.name
        view.findViewById<TextView>(R.id.tvFacilityLocation)?.text = facility.location
        view.findViewById<TextView>(R.id.tvDate)?.text = selectedDate
        view.findViewById<TextView>(R.id.tvDuration)?.text = if (selectedDuration == 8) "Full Day" else "$selectedDuration Hours"
        view.findViewById<TextView>(R.id.tvPriceDetail)?.text = "${facility.name} x $selectedDuration hrs"
    }

    private fun updatePricing() {
        val facility = facility ?: return
        if (selectedDuration == 0) return

        val pricePerDay = facility.price
        val subtotal = when (selectedDuration) {
            1 -> pricePerDay / 8
            2 -> pricePerDay / 4
            4 -> pricePerDay / 2
            8 -> pricePerDay
            else -> 0
        }
        val tax = (subtotal * 0.11).toInt()
        val total = subtotal + tax

        view?.findViewById<TextView>(R.id.tvSubtotal)?.text = subtotal.toRupiah()
        view?.findViewById<TextView>(R.id.tvTax)?.text = tax.toRupiah()
        view?.findViewById<TextView>(R.id.tvTotal)?.text = total.toRupiah()
    }

    private fun setupButtons(view: View) {
        view.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCancel)?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        view.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnConfirm)?.setOnClickListener {
            confirmBooking()
        }
    }

    private fun confirmBooking() {
        val facility = facility ?: return
        if (selectedDuration == 0) return

        val pricePerDay = facility.price
        val subtotal = when (selectedDuration) {
            1 -> pricePerDay / 8
            2 -> pricePerDay / 4
            4 -> pricePerDay / 2
            8 -> pricePerDay
            else -> 0
        }
        val tax = (subtotal * 0.11).toInt()
        val total = subtotal + tax

        val order = OrderHistory(
            id = 0,
            facilityName = facility.name,
            date = selectedDate,
            status = "Diproses",
            totalPrice = total.toRupiah(),
            location = facility.location,
            imageResId = facility.imageResId
        )

        bookingViewModel.confirmBooking(order)
    }

    private fun observeViewModel() {
        bookingViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            view?.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnConfirm)?.isEnabled = !loading
        }

        bookingViewModel.bookingConfirmed.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { bookingId ->
                navigateToSuccess(bookingId)
            }
        }

        bookingViewModel.errorEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToSuccess(bookingId: String) {
        val successFragment = BookingSuccessFragment().apply {
            arguments = Bundle().apply {
                putString("bookingId", bookingId)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, successFragment)
            .addToBackStack(null)
            .commit()
    }
}

class BookingSuccessFragment : Fragment(R.layout.fragment_booking_success) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookingId = arguments?.getString("bookingId") ?: "LF-000000"
        view.findViewById<TextView>(R.id.tvBookingId).text = bookingId

        view.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnBackToHome)?.setOnClickListener {
            parentFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }
}
