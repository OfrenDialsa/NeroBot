package com.example.nerobot.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.nerobot.core.utils.removeAsterisks
import com.example.nerobot.data.local.MessageDataStore
import com.example.nerobot.domain.model.MessageDomainModel
import com.example.nerobot.domain.usecase.sendmessage.SendMessageUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ChatViewModelImpl(
    private val sendMessageUseCase: SendMessageUseCase,
    private val messageDataStore: MessageDataStore
) : ChatViewModel() {

    private val _messageList = MutableStateFlow<List<MessageDomainModel>>(emptyList())
    override val messageList: StateFlow<List<MessageDomainModel>> = _messageList

    init {
        loadMessages()
    }

    private fun loadMessages() {
        viewModelScope.launch {
            _messageList.value = messageDataStore.getMessages.first()
        }
    }

    override fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                _messageList.value = _messageList.value + MessageDomainModel(question, "user")

                val typingMessageIndex = _messageList.value.size
                _messageList.value = _messageList.value + MessageDomainModel("Mengetik.", "model")

                val dots = listOf(".", "..", "...", "....")
                var isTyping = true
                launch {
                    var index = 0
                    while (isTyping) {
                        val animatedMessage = "Mengetik${dots[index % dots.size]}"
                        _messageList.value = _messageList.value.toMutableList().apply {
                            this[typingMessageIndex] = MessageDomainModel(animatedMessage, "model")
                        }
                        index++
                        delay(200)
                    }
                }

                val response = sendMessageUseCase(_messageList.value, question)

                isTyping = false
                val responseMessage = removeAsterisks(response.message)
                val responseRole = response.role
                val animatedResponse = StringBuilder()

                for (char in responseMessage) {
                    animatedResponse.append(char)
                    _messageList.value = _messageList.value.toMutableList().apply {
                        this[typingMessageIndex] = MessageDomainModel(animatedResponse.toString(), responseRole)
                    }
                    delay(10)
                }

                _messageList.value = _messageList.value.dropLast(1) + MessageDomainModel(responseMessage, responseRole)
                messageDataStore.saveMessages(_messageList.value)

            } catch (e: Exception) {
                _messageList.value = _messageList.value.dropLast(1) + MessageDomainModel("Error: " + e.message.toString(), "model")
            }
        }
    }

    override fun clearMessages() {
        _messageList.value = emptyList()
        viewModelScope.launch {
            messageDataStore.saveMessages(emptyList())
        }
    }
}