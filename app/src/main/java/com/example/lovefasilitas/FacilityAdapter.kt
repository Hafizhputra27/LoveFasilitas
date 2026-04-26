package com.example.lovefasilitas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FacilityAdapter(
    private val facilities: List<Facility>
) : RecyclerView.Adapter<FacilityAdapter.FacilityViewHolder>() {

    class FacilityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivFacilityImage: ImageView = itemView.findViewById(R.id.ivFacilityImage)
        val tvFacilityName: TextView = itemView.findViewById(R.id.tvFacilityName)
        val tvFacilityPrice: TextView = itemView.findViewById(R.id.tvFacilityPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_facility, parent, false)
        return FacilityViewHolder(view)
    }

    override fun onBindViewHolder(holder: FacilityViewHolder, position: Int) {
        val facility = facilities[position]
        holder.tvFacilityName.text = facility.name
        holder.tvFacilityPrice.text = facility.price
        holder.ivFacilityImage.setImageResource(facility.imageResId)
    }

    override fun getItemCount(): Int = facilities.size
}
