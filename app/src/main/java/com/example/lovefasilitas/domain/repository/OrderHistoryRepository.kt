package com.example.lovefasilitas.domain.repository

import com.example.lovefasilitas.domain.model.OrderHistory

interface OrderHistoryRepository {
    suspend fun getOrderHistory(): List<OrderHistory>
    suspend fun addOrder(order: OrderHistory)
}
