package com.example.lovefasilitas.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lovefasilitas.domain.common.Event
import com.example.lovefasilitas.domain.model.Facility
import com.example.lovefasilitas.domain.usecase.GetFacilitiesUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getFacilitiesUseCase: GetFacilitiesUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _facilities = MutableLiveData<List<Facility>>()
    val facilities: LiveData<List<Facility>> = _facilities

    private val _errorEvent = MutableLiveData<Event<String>>()
    val errorEvent: LiveData<Event<String>> = _errorEvent

    init {
        loadFacilities()
    }

    fun loadFacilities() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val facilityList = getFacilitiesUseCase()
                _facilities.value = facilityList
            } catch (e: Exception) {
                _errorEvent.value = Event(e.message ?: "Unknown error")
            } finally {
                _isLoading.value = false
            }
        }
    }
}

class HomeViewModelFactory(
    private val getFacilitiesUseCase: GetFacilitiesUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(getFacilitiesUseCase) as T
    }
}
