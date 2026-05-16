package com.example.lovefasilitas.ui.booking

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.lovefasilitas.R
import com.example.lovefasilitas.domain.common.toRupiah
import com.example.lovefasilitas.domain.common.toRupiahPerTrip
import com.example.lovefasilitas.domain.model.Facility
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FacilityDetailFragment : Fragment(R.layout.fragment_facility_detail) {

    private var facility: Facility? = null
    private var selectedDate: String = ""
    private var selectedDuration: Int = 4

    companion object {
        private const val ARG_ID = "id"
        private const val ARG_NAME = "name"
        private const val ARG_PRICE = "price"
        private const val ARG_DESC = "description"
        private const val ARG_IMAGE = "imageResId"
        private const val ARG_CAPACITY = "capacity"
        private const val ARG_LOCATION = "location"

        fun newInstance(facility: Facility): FacilityDetailFragment {
            return FacilityDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, facility.id)
                    putString(ARG_NAME, facility.name)
                    putInt(ARG_PRICE, facility.price)
                    putString(ARG_DESC, facility.description)
                    putInt(ARG_IMAGE, facility.imageResId)
                    putInt(ARG_CAPACITY, facility.capacity)
                    putString(ARG_LOCATION, facility.location)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        if (args == null || !args.containsKey(ARG_ID)) {
            showErrorAndGoBack()
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

        facility?.let { setupUI(it, view) }
        setupDateSelection(view)
        setupDurationChips(view)
        setupBackButton(view)
        setupContinueButton(view)
    }

    private fun showErrorAndGoBack() {
        Snackbar.make(requireView(), "Failed to load facility details", Snackbar.LENGTH_SHORT).show()
        parentFragmentManager.popBackStack()
    }

    private fun setupUI(facility: Facility, view: View) {
        view.findViewById<android.widget.ImageView>(R.id.ivHeroImage)?.setImageResource(facility.imageResId)
        view.findViewById<TextView>(R.id.tvFacilityName)?.text = facility.name
        view.findViewById<TextView>(R.id.tvPrice)?.text = facility.price.toRupiahPerTrip()
        view.findViewById<TextView>(R.id.tvLocation)?.text = facility.location
        view.findViewById<TextView>(R.id.tvDescription)?.text = facility.description
        view.findViewById<TextView>(R.id.tvCapacity)?.text = "${facility.capacity} people"
        view.findViewById<TextView>(R.id.tvRating)?.text = "4.8"
    }

    private fun setupDateSelection(view: View) {
        val dateContainer = view.findViewById<android.widget.LinearLayout>(R.id.dateSelector)
        dateContainer.removeAllViews()

        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)

        for (i in 0 until 7) {
            val day = calendar.clone() as Calendar
            day.add(Calendar.DAY_OF_MONTH, i)
            val dayOfWeek = SimpleDateFormat("EEE", Locale.ENGLISH).format(day.time)
            val dayOfMonth = day.get(Calendar.DAY_OF_MONTH).toString()

            val chip = Chip(requireContext())
            chip.text = "$dayOfMonth\n$dayOfWeek"
            chip.isCheckable = true
            chip.chipBackgroundColor = resources.getColorStateList(R.color.chip_state_list, null)
            chip.setTextColor(resources.getColorStateList(R.color.chip_text_state_list, null))
            chip.textSize = 12f
            chip.typeface = Typeface.DEFAULT_BOLD
            chip.chipStrokeWidth = 0f
            chip.gravity = Gravity.CENTER
            chip.layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                110
            ).apply { marginEnd = 8 }

            if (i == 0) {
                chip.isChecked = true
                selectedDate = dateFormat.format(day.time)
            }
            chip.setOnClickListener {
                for (j in 0 until dateContainer.childCount) {
                    (dateContainer.getChildAt(j) as? Chip)?.isChecked = false
                }
                chip.isChecked = true
                selectedDate = dateFormat.format(day.time)
                updatePricing()
            }

            dateContainer.addView(chip)
        }
    }

    private fun setupDurationChips(view: View) {
        val durationChips = listOf(
            view.findViewById<Chip>(R.id.chipDur1h),
            view.findViewById<Chip>(R.id.chipDur2h),
            view.findViewById<Chip>(R.id.chipDur4h),
            view.findViewById<Chip>(R.id.chipDurFullDay)
        )

        durationChips.forEachIndexed { index, chip ->
            chip?.setOnClickListener {
                durationChips.forEach { it?.isChecked = false }
                chip.isChecked = true
                selectedDuration = when (index) {
                    0 -> 1
                    1 -> 2
                    2 -> 4
                    3 -> 8
                    else -> 0
                }
                updatePricing()
            }
        }

        durationChips.getOrNull(2)?.isChecked = true
        selectedDuration = 4
        updatePricing()
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

    private fun setupBackButton(view: View) {
        view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)?.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupContinueButton(view: View) {
        view.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnContinue)?.setOnClickListener {
            if (selectedDate.isEmpty()) {
                Snackbar.make(view, R.string.msg_select_date, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (selectedDuration == 0) {
                Snackbar.make(view, R.string.msg_select_duration, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fragment = BookingConfirmationFragment.newInstance(
                facility = facility!!,
                date = selectedDate,
                duration = selectedDuration
            )
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
