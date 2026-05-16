package com.example.lovefasilitas.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lovefasilitas.R
import com.example.lovefasilitas.domain.model.Chat

class ChatListAdapter : ListAdapter<Chat, ChatListAdapter.ChatViewHolder>(ChatDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivChatProfile: ImageView = itemView.findViewById(R.id.ivChatProfile)
        private val vOnlineIndicator: View = itemView.findViewById(R.id.vOnlineIndicator)
        private val tvChatName: TextView = itemView.findViewById(R.id.tvChatName)
        private val tvChatMessage: TextView = itemView.findViewById(R.id.tvChatMessage)
        private val tvChatTime: TextView = itemView.findViewById(R.id.tvChatTime)
        private val tvUnreadBadge: TextView = itemView.findViewById(R.id.tvUnreadBadge)

        fun bind(chat: Chat) {
            ivChatProfile.setImageResource(R.drawable.ic_person_outline)
            tvChatName.text = chat.senderName
            tvChatMessage.text = chat.message
            tvChatTime.text = chat.timestamp
            vOnlineIndicator.visibility = if (!chat.isFromCurrentUser) View.VISIBLE else View.GONE

            tvUnreadBadge.visibility = View.GONE
        }
    }

    class ChatDiffCallback : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }
    }
}
