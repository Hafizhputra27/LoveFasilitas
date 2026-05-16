package com.example.lovefasilitas.data.repository

import com.example.lovefasilitas.data.local.UserLocalDataSource
import com.example.lovefasilitas.data.model.toDomain
import com.example.lovefasilitas.data.remote.RemoteUserDataSource
import com.example.lovefasilitas.domain.model.User
import com.example.lovefasilitas.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class UserRepositoryImpl(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: RemoteUserDataSource
) : UserRepository {

    override suspend fun login(email: String, password: String): Result<User> = withContext(Dispatchers.IO) {
        val remoteResult = remoteDataSource.login(email, password)
        remoteResult.fold(
            onSuccess = { user ->
                Result.success(user)
            },
            onFailure = { remoteError ->
                val localResult = localDataSource.login(email, password)
                if (localResult.isSuccess) {
                    Result.success(localResult.getOrThrow().toDomain())
                } else {
                    Result.failure(IOException("Tidak dapat terhubung ke server. ${remoteError.message}"))
                }
            }
        )
    }

    override suspend fun register(
        email: String, name: String, password: String, phone: String
    ): Result<User> = withContext(Dispatchers.IO) {
        val username = email.substringBefore("@")
        val result = remoteDataSource.register(username, name, email, phone, password)
        result.fold(
            onSuccess = { user ->
                localDataSource.register(email, name, password, phone)
                Result.success(user)
            },
            onFailure = { error ->
                Result.failure(error as? IOException ?: IOException("Gagal registrasi: ${error.message}"))
            }
        )
    }

    override suspend fun getCurrentUser(): User? {
        return localDataSource.getCurrentUser()?.toDomain()
    }

    override suspend fun logout() {
        localDataSource.logout()
    }
}
