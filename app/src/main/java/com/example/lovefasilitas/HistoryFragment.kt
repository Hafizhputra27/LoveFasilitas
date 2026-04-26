package com.example.lovefasilitas

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryFragment : Fragment(R.layout.fragment_history) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvOrderHistory: RecyclerView = view.findViewById(R.id.rvOrderHistory)
        val sampleOrders = listOf(
            OrderHistory(1, "Main Hall", "27 Apr 2026", "Rp 10.000.000 / hari", "Placed"),
            OrderHistory(2, "Seminar Room", "28 Apr 2026", "Rp 4.500.000 / hari", "In Progress"),
            OrderHistory(3, "Ball Room", "29 Apr 2026", "Rp 8.000.000 / hari", "Placed"),
            OrderHistory(4, "Auditorium", "30 Apr 2026", "Rp 12.000.000 / hari", "In Progress")
        )

        rvOrderHistory.layoutManager = LinearLayoutManager(requireContext())
        rvOrderHistory.adapter = OrderHistoryAdapter(sampleOrders)
    }
}
