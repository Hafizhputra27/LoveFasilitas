package com.example.lovefasilitas.data.model

import com.example.lovefasilitas.domain.model.OrderHistory

fun OrderHistoryDto.toDomain(): OrderHistory = OrderHistory(
    id = id,
    facilityName = facilityName,
    date = date,
    status = status,
    totalPrice = totalPrice,
    location = location,
    imageResId = imageResId
)

fun OrderHistory.toDto(): OrderHistoryDto = OrderHistoryDto(
    id = id,
    facilityName = facilityName,
    date = date,
    status = status,
    totalPrice = totalPrice,
    location = location,
    imageResId = imageResId
)
