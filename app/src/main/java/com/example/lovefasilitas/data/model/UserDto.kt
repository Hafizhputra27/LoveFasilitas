package com.example.lovefasilitas.data.model

data class UserDto(
    val id: Int,
    val email: String,
    val name: String,
    val phone: String = "",
    val password: String = ""
)
