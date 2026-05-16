package com.example.lovefasilitas.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lovefasilitas.domain.common.Event
import com.example.lovefasilitas.domain.model.User
import com.example.lovefasilitas.domain.usecase.RegisterUserUseCase
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _navigationEvent = MutableLiveData<Event<User>>()
    val navigationEvent: LiveData<Event<User>> = _navigationEvent

    private val _errorEvent = MutableLiveData<Event<String>>()
    val errorEvent: LiveData<Event<String>> = _errorEvent

    fun register(email: String, name: String, password: String, phone: String) {
        viewModelScope.launch {
            _isLoading.value = true
            registerUserUseCase(email, name, password, phone)
                .onSuccess { user ->
                    _isLoading.value = false
                    _navigationEvent.value = Event(user)
                }
                .onFailure { error ->
                    _isLoading.value = false
                    _errorEvent.value = Event(error.message ?: "Registration failed")
                }
        }
    }
}

class RegisterViewModelFactory(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(registerUserUseCase) as T
    }
}
