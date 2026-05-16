package com.example.lovefasilitas.data.model

data class OrderHistoryDto(
    val id: Int,
    val facilityName: String,
    val date: String,
    val status: String,
    val totalPrice: String,
    val location: String = "",
    val imageResId: Int = 0,
    val duration: Int = 0
)
