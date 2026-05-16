package com.example.lovefasilitas.data.local

import com.example.lovefasilitas.R
import com.example.lovefasilitas.data.model.ChatDto
import kotlinx.coroutines.delay

class ChatLocalDataSource {

    private val chatContacts = listOf(
        ChatDto(1, "Admin", "Selamat datang di LoveFasilitas!", "10:00", false, R.drawable.ic_person_outline, true, 2),
        ChatDto(2, "Tunyeng", "Apakah ini masih ada?", "2m ago", false, R.drawable.ic_person_outline, true, 1),
        ChatDto(3, "Fadil", "Masih ada gan?", "1h ago", false, R.drawable.ic_person_outline, false, 0),
        ChatDto(4, "Melpin", "Berapa ini?", "4h ago", false, R.drawable.ic_person_outline, false, 0),
        ChatDto(5, "Alek", "Mau survey boleh?", "Yesterday", false, R.drawable.ic_person_outline, false, 0)
    )

    private val messages = mutableListOf(
        ChatDto(1, "Admin", "Selamat datang di LoveFasilitas!", "10:00", false),
        ChatDto(2, "Anda", "Terima kasih", "10:02", true),
        ChatDto(3, "Admin", "Ada yang bisa saya bantu?", "10:05", false)
    )

    suspend fun getChatContacts(): List<ChatDto> {
        delay(300)
        return chatContacts.toList()
    }

    suspend fun getMessages(): List<ChatDto> {
        delay(200)
        return messages.toList()
    }

    suspend fun sendMessage(message: String) {
        delay(100)
        messages.add(
            ChatDto(
                id = messages.size + 1,
                senderName = "Anda",
                message = message,
                timestamp = "Just now",
                isFromCurrentUser = true
            )
        )
    }
}
