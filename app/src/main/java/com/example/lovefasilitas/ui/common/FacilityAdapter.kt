package com.example.lovefasilitas.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lovefasilitas.databinding.ItemFacilityBinding
import com.example.lovefasilitas.domain.common.toRupiahPerTrip
import com.example.lovefasilitas.domain.model.Facility

class FacilityAdapter(
    private val onItemClick: ((Facility) -> Unit)? = null,
    private val onBookClick: ((Facility) -> Unit)? = null
) : ListAdapter<Facility, FacilityAdapter.FacilityViewHolder>(FacilityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilityViewHolder {
        val binding = ItemFacilityBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FacilityViewHolder(binding, onItemClick, onBookClick)
    }

    override fun onBindViewHolder(holder: FacilityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FacilityViewHolder(
        private val binding: ItemFacilityBinding,
        private val onItemClick: ((Facility) -> Unit)?,
        private val onBookClick: ((Facility) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(facility: Facility) {
            binding.tvFacilityName.text = facility.name
            binding.tvFacilityPrice.text = facility.price.toRupiahPerTrip()
            binding.ivFacilityImage.setImageResource(facility.imageResId)
            binding.tvFacilityLocation.text = facility.location
            binding.tvCapacity.text = "${facility.capacity} people"
            binding.tvRating.text = "4.8"

            val badgeText = when {
                facility.name.contains("Hall", ignoreCase = true) -> "HALL"
                facility.name.contains("Room", ignoreCase = true) -> "ROOM"
                facility.name.contains("Auditorium", ignoreCase = true) -> "AUDITORIUM"
                else -> facility.name.split(" ").firstOrNull()?.uppercase() ?: "FACILITY"
            }
            binding.tvCategoryBadge.text = badgeText

            binding.root.setOnClickListener {
                onItemClick?.invoke(facility)
            }

            binding.btnBookNow.setOnClickListener {
                onBookClick?.invoke(facility)
            }
        }
    }

    class FacilityDiffCallback : DiffUtil.ItemCallback<Facility>() {
        override fun areItemsTheSame(oldItem: Facility, newItem: Facility): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Facility, newItem: Facility): Boolean {
            return oldItem == newItem
        }
    }
}
