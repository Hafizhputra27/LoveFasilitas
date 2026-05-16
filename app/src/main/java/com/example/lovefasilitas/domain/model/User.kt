package com.example.lovefasilitas.domain.model

data class User(
    val id: Int = 0,
    val email: String,
    val name: String,
    val phone: String = "",
    val password: String = ""
)
