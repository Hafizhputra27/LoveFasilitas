package com.example.lovefasilitas.data.repository

import com.example.lovefasilitas.data.local.FacilityLocalDataSource
import com.example.lovefasilitas.data.model.FacilityDto
import com.example.lovefasilitas.data.model.toDomain
import com.example.lovefasilitas.data.model.toDto
import com.example.lovefasilitas.data.remote.FacilityApiModel
import com.example.lovefasilitas.data.remote.FacilityRequest
import com.example.lovefasilitas.data.remote.RemoteFacilityDataSource
import com.example.lovefasilitas.data.remote.toFacilityDto
import com.example.lovefasilitas.domain.model.Facility
import com.example.lovefasilitas.domain.repository.FacilityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class FacilityRepositoryImpl(
    private val localDataSource: FacilityLocalDataSource,
    private val remoteDataSource: RemoteFacilityDataSource
) : FacilityRepository {

    override suspend fun getFacilities(): List<Facility> = withContext(Dispatchers.IO) {
        val result = remoteDataSource.getAllFacilities()
        result.fold(
            onSuccess = { remoteFacilities ->
                val dtos = remoteFacilities.map { it.toDto() }
                localDataSource.syncFacilities(dtos)
                remoteFacilities
            },
            onFailure = {
                localDataSource.getFacilities().map { it.toDomain() }
            }
        )
    }

    override suspend fun getFacilityById(id: Int): Facility? = withContext(Dispatchers.IO) {
        try {
            val result = remoteDataSource.getFacilityById(id)
            result.getOrNull()
        } catch (e: Exception) {
            localDataSource.getFacilityById(id)?.toDomain()
        }
    }

    override suspend fun searchFacilities(query: String): List<Facility> {
        return localDataSource.searchFacilities(query).map { it.toDomain() }
    }

    override suspend fun addFacility(
        name: String, category: String, price: Int, capacity: Int,
        location: String, description: String
    ): Result<Facility> = withContext(Dispatchers.IO) {
        val request = FacilityRequest(name, category, price, capacity, location, description)
        val result = remoteDataSource.addFacility(request)
        result.fold(
            onSuccess = { facility ->
                localDataSource.addFacility(facility.toDto())
                Result.success(facility)
            },
            onFailure = { error ->
                Result.failure(error as? IOException ?: IOException("Gagal menambahkan"))
            }
        )
    }

    override suspend fun updateFacility(
        id: Int, name: String, category: String, price: Int, capacity: Int,
        location: String, description: String
    ): Result<Facility> = withContext(Dispatchers.IO) {
        val request = FacilityRequest(name, category, price, capacity, location, description)
        remoteDataSource.updateFacility(id, request)
    }

    override suspend fun deleteFacility(id: Int): Result<Boolean> = withContext(Dispatchers.IO) {
        val result = remoteDataSource.deleteFacility(id)
        result.fold(
            onSuccess = { success ->
                if (success) localDataSource.removeFacility(id)
                Result.success(success)
            },
            onFailure = { Result.failure(it) }
        )
    }
}
