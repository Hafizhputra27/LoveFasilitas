package com.example.lovefasilitas.domain.model

data class Chat(
    val id: Int,
    val senderName: String,
    val message: String,
    val timestamp: String,
    val isFromCurrentUser: Boolean = false
)