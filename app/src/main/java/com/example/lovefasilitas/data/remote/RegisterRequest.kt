package com.example.lovefasilitas.data.remote

data class RegisterRequest(
    val username: String,
    val name: String,
    val email: String,
    val phone: String,
    val password: String
)
