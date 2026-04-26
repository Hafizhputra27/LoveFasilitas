package com.example.lovefasilitas

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

abstract class PlaceholderFragment : Fragment(R.layout.fragment_placeholder) {

    abstract fun getTitleText(): String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvPlaceholderTitle: TextView = view.findViewById(R.id.tvPlaceholderTitle)
        tvPlaceholderTitle.text = getTitleText()
    }
}
