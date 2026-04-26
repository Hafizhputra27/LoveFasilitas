package com.example.lovefasilitas

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvUsername: TextView = view.findViewById(R.id.tvUsername)
        val rvFacilities: RecyclerView = view.findViewById(R.id.rvFacilities)

        val username = requireActivity().intent.getStringExtra("username") ?: "Guest"
        tvUsername.text = "Username: $username"

        val facilities = listOf(
            Facility(1, "Main Hall", "Rp 10.000.000 / hari", R.drawable.sample_main_hall),
            Facility(2, "Seminar Room", "Rp 4.500.000 / hari", R.drawable.sample_seminar_room),
            Facility(3, "Ball Room", "Rp 8.000.000 / hari", R.drawable.sample_ball_room),
            Facility(4, "Auditorium", "Rp 12.000.000 / hari", R.drawable.sample_auditorium)
        )

        rvFacilities.layoutManager = LinearLayoutManager(requireContext())
        rvFacilities.adapter = FacilityAdapter(facilities)
    }
}
