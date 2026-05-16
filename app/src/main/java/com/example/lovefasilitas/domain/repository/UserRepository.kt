package com.example.lovefasilitas.domain.repository

import com.example.lovefasilitas.domain.model.User

interface UserRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(email: String, name: String, password: String, phone: String): Result<User>
    suspend fun getCurrentUser(): User?
    suspend fun logout()
}