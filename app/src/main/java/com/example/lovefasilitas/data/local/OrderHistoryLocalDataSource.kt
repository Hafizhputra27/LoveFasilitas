package com.example.lovefasilitas.data.local

import com.example.lovefasilitas.R
import com.example.lovefasilitas.data.model.OrderHistoryDto
import kotlinx.coroutines.delay

class OrderHistoryLocalDataSource {

    private val orders = mutableListOf(
        OrderHistoryDto(1, "Main Hall", "20 Jan 2024", "Selesai", "Rp 10.000.000", "Building A, Floor 1", R.drawable.sample_main_hall, 4),
        OrderHistoryDto(2, "Seminar Room", "15 Feb 2024", "Selesai", "Rp 4.500.000", "Building A, Floor 2", R.drawable.sample_seminar_room, 2),
        OrderHistoryDto(3, "Ball Room", "10 Mar 2024", "Diproses", "Rp 8.000.000", "Building B, Floor 1", R.drawable.sample_ball_room, 8)
    )

    suspend fun getOrders(): List<OrderHistoryDto> {
        delay(300)
        return orders.toList()
    }

    suspend fun addOrder(order: OrderHistoryDto) {
        delay(200)
        orders.add(0, order)
    }
}
