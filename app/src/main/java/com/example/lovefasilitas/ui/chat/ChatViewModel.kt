package com.example.lovefasilitas.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lovefasilitas.domain.common.Event
import com.example.lovefasilitas.domain.model.Chat
import com.example.lovefasilitas.domain.usecase.GetChatListUseCase
import kotlinx.coroutines.launch

class ChatViewModel(
    private val getChatListUseCase: GetChatListUseCase
) : ViewModel() {

    private val _chatList = MutableLiveData<List<Chat>>()
    val chatList: LiveData<List<Chat>> = _chatList

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorEvent = MutableLiveData<Event<String>>()
    val errorEvent: LiveData<Event<String>> = _errorEvent

    init {
        loadChats()
    }

    fun loadChats() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val contacts = getChatListUseCase()
                _chatList.value = contacts
            } catch (e: Exception) {
                _errorEvent.value = Event(e.message ?: "Failed to load chats")
            } finally {
                _isLoading.value = false
            }
        }
    }
}

class ChatViewModelFactory(
    private val getChatListUseCase: GetChatListUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel(getChatListUseCase) as T
    }
}
