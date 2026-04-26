package com.example.lovefasilitas

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatFragment : Fragment(R.layout.fragment_chat) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvChats: RecyclerView = view.findViewById(R.id.rvChats)
        val sampleChats = listOf(
            Chat(1, "Alex", "Halo, fasilitasnya masih tersedia?", "09:12"),
            Chat(2, "Edo", "Siap, saya booking untuk besok.", "08:45"),
            Chat(3, "Tunyeng", "Boleh minta detail harga paket?", "Kemarin"),
            Chat(4, "Fadil", "Terima kasih, infonya sangat membantu.", "Kemarin")
        )

        rvChats.layoutManager = LinearLayoutManager(requireContext())
        rvChats.adapter = ChatAdapter(sampleChats)
    }
}
