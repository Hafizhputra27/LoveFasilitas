package com.example.lovefasilitas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(
    private val listEvent: List<Event>,
    private val onItemClick: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvEventName)
        val tvDate: TextView = itemView.findViewById(R.id.tvEventDate)
        val tvLocation: TextView = itemView.findViewById(R.id.tvEventLocation)
        val tvPrice: TextView = itemView.findViewById(R.id.tvEventPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = listEvent[position]
        holder.tvName.text = event.name
        holder.tvDate.text = event.date
        holder.tvLocation.text = event.location
        holder.tvPrice.text = event.getFormattedPrice()

        // Set price color: Green if free, Black if paid
        if (event.price == 0) {
            holder.tvPrice.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.holo_green_dark))
        } else {
            holder.tvPrice.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.black))
        }

        holder.itemView.setOnClickListener {
            onItemClick(event)
        }
    }

    override fun getItemCount(): Int = listEvent.size
}
