package com.example.lovefasilitas.domain.usecase

import com.example.lovefasilitas.domain.model.Chat
import com.example.lovefasilitas.domain.repository.ChatRepository

class GetChatListUseCase(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(): List<Chat> {
        return chatRepository.getChatContacts()
    }
}
