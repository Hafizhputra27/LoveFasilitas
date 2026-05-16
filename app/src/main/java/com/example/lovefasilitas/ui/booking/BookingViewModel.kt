package com.example.lovefasilitas.ui.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lovefasilitas.domain.common.Event
import com.example.lovefasilitas.domain.model.OrderHistory
import com.example.lovefasilitas.domain.usecase.AddOrderUseCase
import kotlinx.coroutines.launch

class BookingViewModel(
    private val addOrderUseCase: AddOrderUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _bookingConfirmed = MutableLiveData<Event<String>>()
    val bookingConfirmed: LiveData<Event<String>> = _bookingConfirmed

    private val _errorEvent = MutableLiveData<Event<String>>()
    val errorEvent: LiveData<Event<String>> = _errorEvent

    fun confirmBooking(order: OrderHistory) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                addOrderUseCase(order)
                val bookingId = "LF-${System.currentTimeMillis()}"
                _bookingConfirmed.value = Event(bookingId)
            } catch (e: Exception) {
                _errorEvent.value = Event(e.message ?: "Booking failed")
            } finally {
                _isLoading.value = false
            }
        }
    }
}

class BookingViewModelFactory(
    private val addOrderUseCase: AddOrderUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookingViewModel(addOrderUseCase) as T
    }
}
