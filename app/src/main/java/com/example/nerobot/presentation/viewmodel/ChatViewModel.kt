package com.example.nerobot.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.nerobot.domain.model.MessageDomainModel
import kotlinx.coroutines.flow.StateFlow

abstract class ChatViewModel : ViewModel() {
    abstract val messageList: StateFlow<List<MessageDomainModel>>
    abstract val isModelResponding: StateFlow<Boolean>
    abstract fun sendMessage(question: String)
    abstract fun clearMessages()
    abstract fun skipResponse()
}