package com.example.lovefasilitas.data.local

import com.example.lovefasilitas.R
import com.example.lovefasilitas.data.model.FacilityDto
import kotlinx.coroutines.delay

class FacilityLocalDataSource {

    private val facilities = mutableListOf(
        FacilityDto(
            id = 1, name = "Main Hall", price = 10_000_000,
            imageResId = R.drawable.sample_main_hall,
            description = "Large main hall for events and conferences.",
            capacity = 500, location = "Building A, Floor 1",
            category = "hall", rating = 4.7
        ),
        FacilityDto(
            id = 2, name = "Seminar Room", price = 4_500_000,
            imageResId = R.drawable.sample_seminar_room,
            description = "Medium-sized room for seminars and workshops.",
            capacity = 100, location = "Building A, Floor 2",
            category = "room", rating = 4.5
        ),
        FacilityDto(
            id = 3, name = "Ball Room", price = 8_000_000,
            imageResId = R.drawable.sample_ball_room,
            description = "Elegant ball room for celebrations.",
            capacity = 300, location = "Building B, Floor 1",
            category = "hall", rating = 4.3
        ),
        FacilityDto(
            id = 4, name = "Auditorium", price = 12_000_000,
            imageResId = R.drawable.sample_auditorium,
            description = "Professional auditorium for large gatherings.",
            capacity = 800, location = "Building C, Floor 1",
            category = "hall", rating = 4.8
        ),
        FacilityDto(
            id = 5, name = "Creative Studio", price = 3_500_000,
            imageResId = R.drawable.sample_ball_room,
            description = "Modern creative studio with professional lighting.",
            capacity = 30, location = "Building D, Floor 3",
            category = "studio", rating = 4.2
        )
    )

    suspend fun getFacilities(): List<FacilityDto> {
        delay(500)
        return facilities.toList()
    }

    suspend fun getFacilityById(id: Int): FacilityDto? {
        delay(200)
        return facilities.find { it.id == id }
    }

    suspend fun searchFacilities(query: String): List<FacilityDto> {
        delay(300)
        return facilities.filter {
            it.name.contains(query, ignoreCase = true) ||
            it.description.contains(query, ignoreCase = true)
        }
    }

    fun syncFacilities(remote: List<FacilityDto>) {
        facilities.clear()
        facilities.addAll(remote)
    }

    fun addFacility(facility: FacilityDto) {
        facilities.add(0, facility)
    }

    fun removeFacility(id: Int) {
        facilities.removeAll { it.id == id }
    }
}
