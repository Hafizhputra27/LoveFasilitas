package com.example.lovefasilitas

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderHistoryAdapter(
    private val orders: List<OrderHistory>
) : RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {

    class OrderHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFacilityName: TextView = itemView.findViewById(R.id.tvOrderFacilityName)
        val tvBookingDate: TextView = itemView.findViewById(R.id.tvOrderBookingDate)
        val tvPrice: TextView = itemView.findViewById(R.id.tvOrderPrice)
        val tvStatus: TextView = itemView.findViewById(R.id.tvOrderStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_history, parent, false)
        return OrderHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        val order = orders[position]
        holder.tvFacilityName.text = order.facilityName
        holder.tvBookingDate.text = "Tanggal: ${order.bookingDate}"
        holder.tvPrice.text = order.totalPrice
        holder.tvStatus.text = order.status

        val statusColor = when (order.status) {
            "Placed" -> "#1976D2"
            "In Progress" -> "#F57C00"
            else -> "#666666"
        }
        holder.tvStatus.setTextColor(Color.parseColor(statusColor))
    }

    override fun getItemCount(): Int = orders.size
}
