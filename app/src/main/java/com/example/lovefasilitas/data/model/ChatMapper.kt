package com.example.lovefasilitas.data.model

import com.example.lovefasilitas.domain.model.Chat

fun ChatDto.toDomain(): Chat = Chat(
    id = id,
    senderName = senderName,
    message = message,
    timestamp = timestamp,
    isFromCurrentUser = isFromCurrentUser
)

fun Chat.toDto(): ChatDto = ChatDto(
    id = id,
    senderName = senderName,
    message = message,
    timestamp = timestamp,
    isFromCurrentUser = isFromCurrentUser
)
