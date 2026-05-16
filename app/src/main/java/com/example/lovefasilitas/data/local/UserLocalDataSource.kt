package com.example.lovefasilitas.data.local

import com.example.lovefasilitas.data.model.UserDto
import kotlinx.coroutines.delay

class UserLocalDataSource {

    private var currentUser: UserDto? = null

    private val mockUsers = mutableMapOf(
        "admin" to UserDto(
            id = 1,
            email = "admin",
            name = "Administrator",
            phone = "081234567890",
            password = "admin123"
        )
    )

    suspend fun login(email: String, password: String): Result<UserDto> {
        delay(500)
        val user = mockUsers[email]
        return if (user != null && user.password == password) {
            currentUser = user
            Result.success(user)
        } else {
            Result.failure(IllegalArgumentException("Email atau Password salah!"))
        }
    }

    suspend fun register(
        email: String,
        name: String,
        password: String,
        phone: String
    ): Result<UserDto> {
        delay(500)
        return if (mockUsers.containsKey(email)) {
            Result.failure(IllegalArgumentException("Email sudah terdaftar!"))
        } else {
            val newUser = UserDto(
                id = mockUsers.size + 1,
                email = email,
                name = name,
                phone = phone,
                password = password
            )
            mockUsers[email] = newUser
            currentUser = newUser
            Result.success(newUser)
        }
    }

    fun getCurrentUser(): UserDto? = currentUser

    fun logout() {
        currentUser = null
    }
}
