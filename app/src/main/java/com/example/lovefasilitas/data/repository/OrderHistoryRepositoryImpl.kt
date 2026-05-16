package com.example.lovefasilitas.data.repository

import com.example.lovefasilitas.data.local.OrderHistoryLocalDataSource
import com.example.lovefasilitas.data.model.toDomain
import com.example.lovefasilitas.data.model.toDto
import com.example.lovefasilitas.domain.model.OrderHistory
import com.example.lovefasilitas.domain.repository.OrderHistoryRepository

class OrderHistoryRepositoryImpl(
    private val localDataSource: OrderHistoryLocalDataSource
) : OrderHistoryRepository {

    override suspend fun getOrderHistory(): List<OrderHistory> {
        return localDataSource.getOrders().map { it.toDomain() }
    }

    override suspend fun addOrder(order: OrderHistory) {
        localDataSource.addOrder(order.toDto())
    }
}
