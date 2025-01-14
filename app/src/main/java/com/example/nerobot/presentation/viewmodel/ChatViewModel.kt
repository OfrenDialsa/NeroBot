package com.example.nerobot.presentation.viewmodel

import com.example.nerobot.domain.model.MessageDomainModel
import kotlinx.coroutines.flow.StateFlow

interface ChatViewModel {
    val messageList: StateFlow<List<MessageDomainModel>>
    val isModelResponding: StateFlow<Boolean>
    fun sendMessage(question: String, image: String?)
    fun clearMessages()
    fun skipResponse()
}