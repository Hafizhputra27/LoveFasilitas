package com.example.lovefasilitas.data.model

data class FacilityDto(
    val id: Int,
    val name: String,
    val price: Int,
    val imageResId: Int,
    val description: String = "",
    val capacity: Int = 0,
    val location: String = "",
    val category: String = "",
    val rating: Double = 0.0
)
