package com.example.lovefasilitas.data.remote

import com.example.lovefasilitas.domain.model.Facility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

class RemoteFacilityDataSource(
    private val apiService: ApiService
) {

    suspend fun getAllFacilities(): Result<List<Facility>> = withContext(Dispatchers.IO) {
        safeApiCall {
            apiService.getAllFacilities()
        }.map { response ->
            val data = response.data ?: emptyList()
            data.map { it.toFacility() }
        }
    }

    suspend fun getFacilityById(id: Int): Result<Facility> = withContext(Dispatchers.IO) {
        safeApiCall {
            apiService.getFacilityById(id)
        }.map { response ->
            val list = response.data ?: emptyList()
            list.firstOrNull()?.toFacility()
                ?: throw IOException("Fasilitas tidak ditemukan")
        }
    }

    suspend fun getFacilitiesByCategory(category: String): Result<List<Facility>> = withContext(Dispatchers.IO) {
        safeApiCall {
            apiService.getFacilitiesByCategory(category)
        }.map { response ->
            val data = response.data ?: emptyList()
            data.map { it.toFacility() }
        }
    }

    suspend fun addFacility(request: FacilityRequest): Result<Facility> = withContext(Dispatchers.IO) {
        safeApiCall {
            apiService.addFacility(request)
        }.map { response ->
            val list = response.data ?: emptyList()
            list.firstOrNull()?.toFacility()
                ?: throw IOException("Gagal menambahkan fasilitas")
        }
    }

    suspend fun updateFacility(id: Int, request: FacilityRequest): Result<Facility> = withContext(Dispatchers.IO) {
        safeApiCall {
            apiService.updateFacility(id = id, facility = request)
        }.map { response ->
            val list = response.data ?: emptyList()
            list.firstOrNull()?.toFacility()
                ?: throw IOException("Gagal mengupdate fasilitas")
        }
    }

    suspend fun deleteFacility(id: Int): Result<Boolean> = withContext(Dispatchers.IO) {
        safeApiCall {
            apiService.deleteFacility(id = id)
        }.map { response ->
            response.success
        }
    }
}

suspend fun <T> safeApiCall(call: suspend () -> ApiResponse<T>): Result<ApiResponse<T>> {
    return try {
        val response = call()
        if (response.success) {
            Result.success(response)
        } else {
            Result.failure(IOException(response.message))
        }
    } catch (e: IOException) {
        Result.failure(e)
    } catch (e: Exception) {
        Result.failure(IOException("Network error: ${e.message}"))
    }
}
