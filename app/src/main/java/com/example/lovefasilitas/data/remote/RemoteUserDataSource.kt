package com.example.lovefasilitas.data.remote

import com.example.lovefasilitas.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class RemoteUserDataSource(
    private val apiService: ApiService
) {

    suspend fun login(email: String, password: String): Result<User> = withContext(Dispatchers.IO) {
        safeApiCall {
            val request = LoginRequest(email, password)
            apiService.login(request = request)
        }.map { response ->
            response.data?.toDomain()
                ?: throw IOException("Login gagal: data tidak valid")
        }
    }

    suspend fun register(
        username: String,
        name: String,
        email: String,
        phone: String,
        password: String
    ): Result<User> = withContext(Dispatchers.IO) {
        safeApiCall {
            val request = RegisterRequest(username, name, email, phone, password)
            apiService.register(request = request)
        }.map { response ->
            response.data?.toDomain()
                ?: throw IOException("Registrasi gagal: data tidak valid")
        }
    }
}
