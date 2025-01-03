package com.example.nerobot.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.nerobot.data.model.MessageModel
import com.example.nerobot.domain.usecase.SendMessageUseCase
import kotlinx.coroutines.launch

class ChatViewModelImpl(private val sendMessageUseCase: SendMessageUseCase) : ChatViewModel() {

    override val messageList = mutableStateListOf<MessageModel>()

    override fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                messageList.add(MessageModel(question, "user"))
                messageList.add(MessageModel("Mengetik.....", "model"))

                val response = sendMessageUseCase(messageList, question)
                messageList.removeAt(messageList.lastIndex)
                messageList.add(response)

            } catch (e: Exception) {
                messageList.removeAt(messageList.lastIndex)
                messageList.add(MessageModel("Error: " + e.message.toString(), "model"))
            }
        }
    }
}