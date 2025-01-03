package com.example.nerobot.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nerobot.data.model.MessageModel
import com.example.nerobot.domain.usecase.sendmessage.SendMessageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModelImpl(
    private val sendMessageUseCase: SendMessageUseCase
) : ChatViewModel() {

    private val _messageList = MutableStateFlow<List<MessageModel>>(emptyList())
    override val messageList: StateFlow<List<MessageModel>> = _messageList

    override fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                _messageList.value = _messageList.value + MessageModel(question, "user")
                _messageList.value = _messageList.value + MessageModel("Mengetik.....", "model")

                val response = sendMessageUseCase(_messageList.value, question)
                _messageList.value = _messageList.value.dropLast(1) + response

            } catch (e: Exception) {
                _messageList.value = _messageList.value.dropLast(1) + MessageModel("Error: " + e.message.toString(), "model")
            }
        }
    }
}