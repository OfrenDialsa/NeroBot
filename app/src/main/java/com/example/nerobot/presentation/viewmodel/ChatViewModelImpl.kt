package com.example.nerobot.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.nerobot.core.util.removeAsterisks
import com.example.nerobot.domain.model.MessageDomainModel
import com.example.nerobot.domain.usecase.sendmessage.SendMessageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModelImpl(
    private val sendMessageUseCase: SendMessageUseCase
) : ChatViewModel() {

    private val _messageList = MutableStateFlow<List<MessageDomainModel>>(emptyList())
    override val messageList: StateFlow<List<MessageDomainModel>> = _messageList

    override fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                _messageList.value = _messageList.value + MessageDomainModel(question, "user")
                _messageList.value = _messageList.value + MessageDomainModel("Mengetik.....", "model")

                val response = sendMessageUseCase(_messageList.value, question)

                val cleanedResponse = MessageDomainModel(
                    message = removeAsterisks(response.message),
                    role = response.role
                )

                _messageList.value = _messageList.value.dropLast(1) + cleanedResponse

            } catch (e: Exception) {
                _messageList.value = _messageList.value.dropLast(1) + MessageDomainModel("Error: " + e.message.toString(), "model")
            }
        }
    }

    override fun clearMessages() {
        _messageList.value = emptyList()
    }

}