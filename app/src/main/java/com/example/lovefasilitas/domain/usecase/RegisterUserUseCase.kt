package com.example.lovefasilitas.domain.usecase

import com.example.lovefasilitas.domain.model.User
import com.example.lovefasilitas.domain.repository.UserRepository

class RegisterUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        email: String,
        name: String,
        password: String,
        phone: String
    ): Result<User> {
        if (email.isBlank() || name.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Data tidak boleh kosong!"))
        }
        return userRepository.register(email, name, password, phone)
    }
}