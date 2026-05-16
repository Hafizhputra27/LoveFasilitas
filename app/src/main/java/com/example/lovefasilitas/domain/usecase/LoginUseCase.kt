package com.example.lovefasilitas.domain.usecase

import com.example.lovefasilitas.domain.model.User
import com.example.lovefasilitas.domain.repository.UserRepository

class LoginUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Email dan Password tidak boleh kosong!"))
        }
        return userRepository.login(email, password)
    }
}