package com.example.lovefasilitas.domain.usecase

import com.example.lovefasilitas.domain.model.OrderHistory
import com.example.lovefasilitas.domain.repository.OrderHistoryRepository

class AddOrderUseCase(
    private val orderHistoryRepository: OrderHistoryRepository
) {
    suspend operator fun invoke(order: OrderHistory) {
        orderHistoryRepository.addOrder(order)
    }
}
