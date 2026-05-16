package com.example.lovefasilitas.data.remote

data class FacilityRequest(
    val name: String,
    val category: String,
    val price: Int,
    val capacity: Int,
    val location: String,
    val description: String = "",
    val rating: Double = 0.0
)
