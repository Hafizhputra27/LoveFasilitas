package com.example.lovefasilitas.domain.repository

import com.example.lovefasilitas.domain.model.Chat

interface ChatRepository {
    suspend fun getChatContacts(): List<Chat>
    suspend fun getMessages(): List<Chat>
    suspend fun sendMessage(message: String)
}
