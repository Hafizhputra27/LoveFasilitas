package com.example.lovefasilitas.data.remote

import com.example.lovefasilitas.R
import com.example.lovefasilitas.data.model.FacilityDto
import com.example.lovefasilitas.domain.model.Facility
import com.google.gson.annotations.SerializedName

data class FacilityApiModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("category") val category: String = "",
    @SerializedName("price") val price: String,
    @SerializedName("capacity") val capacity: String,
    @SerializedName("location") val location: String,
    @SerializedName("description") val description: String = "",
    @SerializedName("rating") val rating: String = "0.0"
)

fun FacilityApiModel.toFacility(): Facility = Facility(
    id = id.toIntOrNull() ?: 0,
    name = name,
    price = price.toIntOrNull() ?: 0,
    imageResId = mapCategoryToImage(category),
    description = description,
    capacity = capacity.toIntOrNull() ?: 0,
    location = location,
    category = category,
    rating = rating.toDoubleOrNull() ?: 0.0
)

fun FacilityApiModel.toFacilityDto(): FacilityDto = FacilityDto(
    id = id.toIntOrNull() ?: 0,
    name = name,
    price = price.toIntOrNull() ?: 0,
    imageResId = mapCategoryToImage(category),
    description = description,
    capacity = capacity.toIntOrNull() ?: 0,
    location = location,
    category = category,
    rating = rating.toDoubleOrNull() ?: 0.0
)

private fun mapCategoryToImage(category: String): Int = when (category.lowercase()) {
    "hall" -> R.drawable.sample_main_hall
    "room" -> R.drawable.sample_seminar_room
    "studio" -> R.drawable.sample_ball_room
    "outdoor" -> R.drawable.sample_auditorium
    else -> R.drawable.sample_main_hall
}
