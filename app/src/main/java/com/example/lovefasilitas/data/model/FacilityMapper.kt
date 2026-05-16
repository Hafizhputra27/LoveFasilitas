package com.example.lovefasilitas.data.model

import com.example.lovefasilitas.domain.model.Facility

fun FacilityDto.toDomain(): Facility = Facility(
    id = id,
    name = name,
    price = price,
    imageResId = imageResId,
    description = description,
    capacity = capacity,
    location = location,
    category = category,
    rating = rating
)

fun Facility.toDto(): FacilityDto = FacilityDto(
    id = id,
    name = name,
    price = price,
    imageResId = imageResId,
    description = description,
    capacity = capacity,
    location = location,
    category = category,
    rating = rating
)
