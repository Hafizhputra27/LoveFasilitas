package com.example.lovefasilitas.data.model

data class ChatDto(
    val id: Int,
    val senderName: String,
    val message: String,
    val timestamp: String,
    val isFromCurrentUser: Boolean = false,
    val profileImage: Int = 0,
    val isOnline: Boolean = false,
    val unreadCount: Int = 0
)
