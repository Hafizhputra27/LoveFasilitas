package com.example.lovefasilitas.domain.repository

import com.example.lovefasilitas.domain.model.Facility

interface FacilityRepository {
    suspend fun getFacilities(): List<Facility>
    suspend fun getFacilityById(id: Int): Facility?
    suspend fun searchFacilities(query: String): List<Facility>
    suspend fun addFacility(name: String, category: String, price: Int, capacity: Int, location: String, description: String): Result<Facility>
    suspend fun updateFacility(id: Int, name: String, category: String, price: Int, capacity: Int, location: String, description: String): Result<Facility>
    suspend fun deleteFacility(id: Int): Result<Boolean>
}
