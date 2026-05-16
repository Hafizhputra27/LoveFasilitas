package com.example.lovefasilitas.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lovefasilitas.LoveFasilitasApp
import com.example.lovefasilitas.R
import com.example.lovefasilitas.domain.usecase.GetOrderHistoryUseCase
import com.example.lovefasilitas.ui.common.OrderHistoryAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val viewModel: HistoryViewModel by lazy {
        val app = requireActivity().application as LoveFasilitasApp
        val getOrderHistoryUseCase = GetOrderHistoryUseCase(app.container.orderHistoryRepository)
        ViewModelProvider(this, HistoryViewModelFactory(getOrderHistoryUseCase))[HistoryViewModel::class.java]
    }

    private val adapter = OrderHistoryAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvOrderHistory = view.findViewById<RecyclerView>(R.id.rvOrderHistory)
        rvOrderHistory.layoutManager = LinearLayoutManager(requireContext())
        rvOrderHistory.adapter = adapter

        setupFilterChips(view)
        observeViewModel()
    }

    private fun setupFilterChips(view: View) {
        view.findViewById<Chip>(R.id.chipAll)?.setOnClickListener { filterOrders(null) }
        view.findViewById<Chip>(R.id.chipCompleted)?.setOnClickListener { filterOrders("Selesai") }
        view.findViewById<Chip>(R.id.chipInProgress)?.setOnClickListener { filterOrders("Diproses") }
    }

    private fun filterOrders(status: String?) {
        val allOrders = viewModel.orderHistory.value ?: return
        val filtered = if (status == null) {
            allOrders
        } else {
            allOrders.filter { it.status.equals(status, ignoreCase = true) }
        }
        adapter.submitList(filtered)
    }

    private fun observeViewModel() {
        viewModel.orderHistory.observe(viewLifecycleOwner) { orders ->
            val view = requireView()
            val recyclerView = view.findViewById<RecyclerView>(R.id.rvOrderHistory)
            val emptyState = view.findViewById<View>(R.id.emptyState)

            if (orders.isEmpty()) {
                recyclerView.visibility = View.GONE
                emptyState.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                emptyState.visibility = View.GONE
                adapter.submitList(orders)
            }
        }

        viewModel.errorEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
