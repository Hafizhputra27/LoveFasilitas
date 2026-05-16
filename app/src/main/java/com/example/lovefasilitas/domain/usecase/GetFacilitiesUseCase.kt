package com.example.lovefasilitas.domain.usecase

import com.example.lovefasilitas.domain.model.Facility
import com.example.lovefasilitas.domain.repository.FacilityRepository

class GetFacilitiesUseCase(
    private val facilityRepository: FacilityRepository
) {
    suspend operator fun invoke(): List<Facility> {
        return facilityRepository.getFacilities()
    }
}