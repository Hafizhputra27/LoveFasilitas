package com.example.lovefasilitas.domain.model

data class OrderHistory(
    val id: Int,
    val facilityName: String,
    val date: String,
    val status: String,
    val totalPrice: String,
    val location: String = "",
    val imageResId: Int = 0
)
