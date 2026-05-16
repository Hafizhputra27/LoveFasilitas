package com.example.lovefasilitas.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lovefasilitas.domain.common.Event
import com.example.lovefasilitas.domain.model.OrderHistory
import com.example.lovefasilitas.domain.usecase.GetOrderHistoryUseCase
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val getOrderHistoryUseCase: GetOrderHistoryUseCase
) : ViewModel() {

    private val _orderHistory = MutableLiveData<List<OrderHistory>>()
    val orderHistory: LiveData<List<OrderHistory>> = _orderHistory

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorEvent = MutableLiveData<Event<String>>()
    val errorEvent: LiveData<Event<String>> = _errorEvent

    init {
        loadHistory()
    }

    fun loadHistory() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val orders = getOrderHistoryUseCase()
                _orderHistory.value = orders
            } catch (e: Exception) {
                _errorEvent.value = Event(e.message ?: "Failed to load history")
            } finally {
                _isLoading.value = false
            }
        }
    }
}

class HistoryViewModelFactory(
    private val getOrderHistoryUseCase: GetOrderHistoryUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HistoryViewModel(getOrderHistoryUseCase) as T
    }
}
