package com.example.lovefasilitas.domain.usecase

import com.example.lovefasilitas.domain.model.OrderHistory
import com.example.lovefasilitas.domain.repository.OrderHistoryRepository

class GetOrderHistoryUseCase(
    private val orderHistoryRepository: OrderHistoryRepository
) {
    suspend operator fun invoke(): List<OrderHistory> {
        return orderHistoryRepository.getOrderHistory()
    }
}
