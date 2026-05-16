package com.example.lovefasilitas.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lovefasilitas.R
import com.example.lovefasilitas.domain.model.OrderHistory
import com.google.android.material.chip.Chip

class OrderHistoryAdapter : ListAdapter<OrderHistory, OrderHistoryAdapter.OrderHistoryViewHolder>(OrderHistoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_history, parent, false)
        return OrderHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OrderHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivOrderImage: ImageView = itemView.findViewById(R.id.ivOrderImage)
        private val chipStatus: Chip = itemView.findViewById(R.id.chipStatus)
        private val tvFacilityName: TextView = itemView.findViewById(R.id.tvOrderFacilityName)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvOrderPrice)
        private val tvLocation: TextView = itemView.findViewById(R.id.tvOrderLocation)
        private val tvBookingDate: TextView = itemView.findViewById(R.id.tvOrderBookingDate)
        private val btnSecondary: com.google.android.material.button.MaterialButton = itemView.findViewById(R.id.btnOrderSecondary)
        private val btnAction: com.google.android.material.button.MaterialButton = itemView.findViewById(R.id.btnOrderAction)

        fun bind(order: OrderHistory) {
            if (order.imageResId != 0) {
                ivOrderImage.setImageResource(order.imageResId)
            }
            chipStatus.text = order.status
            tvFacilityName.text = order.facilityName
            tvPrice.text = order.totalPrice
            tvLocation.text = order.location
            tvBookingDate.text = order.date

            val statusColor = when {
                order.status.equals("Selesai", ignoreCase = true) -> {
                    ContextCompat.getColor(itemView.context, R.color.success_green)
                }
                order.status.equals("Diproses", ignoreCase = true) -> {
                    ContextCompat.getColor(itemView.context, R.color.warning)
                }
                else -> {
                    ContextCompat.getColor(itemView.context, R.color.primary)
                }
            }
            chipStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
            chipStatus.chipBackgroundColor = android.content.res.ColorStateList.valueOf(statusColor)

            btnAction.text = if (order.status.equals("Selesai", ignoreCase = true)) {
                "Book Again"
            } else {
                "View Details"
            }
            btnSecondary.visibility = if (order.status.equals("Selesai", ignoreCase = true)) {
                View.GONE
            } else {
                View.VISIBLE
            }
            btnSecondary.text = "Review"
        }
    }

    class OrderHistoryDiffCallback : DiffUtil.ItemCallback<OrderHistory>() {
        override fun areItemsTheSame(oldItem: OrderHistory, newItem: OrderHistory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OrderHistory, newItem: OrderHistory): Boolean {
            return oldItem == newItem
        }
    }
}
