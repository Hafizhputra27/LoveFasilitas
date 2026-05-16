package com.example.lovefasilitas.data.model

import com.example.lovefasilitas.domain.model.User

fun UserDto.toDomain(): User = User(
    id = id,
    email = email,
    name = name,
    phone = phone,
    password = password
)

fun User.toDto(): UserDto = UserDto(
    id = id,
    email = email,
    name = name,
    phone = phone,
    password = password
)
