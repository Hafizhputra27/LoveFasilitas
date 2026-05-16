package com.example.lovefasilitas.data.repository

import com.example.lovefasilitas.data.local.ChatLocalDataSource
import com.example.lovefasilitas.data.model.toDomain
import com.example.lovefasilitas.domain.model.Chat
import com.example.lovefasilitas.domain.repository.ChatRepository

class ChatRepositoryImpl(
    private val localDataSource: ChatLocalDataSource
) : ChatRepository {

    override suspend fun getChatContacts(): List<Chat> {
        return localDataSource.getChatContacts().map { it.toDomain() }
    }

    override suspend fun getMessages(): List<Chat> {
        return localDataSource.getMessages().map { it.toDomain() }
    }

    override suspend fun sendMessage(message: String) {
        localDataSource.sendMessage(message)
    }
}
