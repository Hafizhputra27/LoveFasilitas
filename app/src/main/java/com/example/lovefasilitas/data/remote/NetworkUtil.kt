package com.example.lovefasilitas.data.remote

import java.io.IOException

/**
 * Helper untuk convert network Result ke domain Result.
 * Jika network gagal, return failure dengan pesan error informatif.
 */
fun <T> httpResultToDomain(
    networkResult: Result<T>,
    offlineMessage: String = "Tidak dapat terhubung ke server"
): Result<T> {
    return networkResult.fold(
        onSuccess = { Result.success(it) },
        onFailure = { error ->
            val message = when (error) {
                is IOException -> error.message ?: offlineMessage
                else -> error.message ?: offlineMessage
            }
            Result.failure(IOException(message))
        }
    )
}
