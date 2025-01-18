package com.nerodev.nerobot.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerodev.nerobot.core.utils.removeAsterisks
import com.nerodev.nerobot.data.local.MessageDataStore
import com.nerodev.nerobot.domain.model.MessageDomainModel
import com.nerodev.nerobot.domain.usecase.sendmessage.SendMessageUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModelImpl(
    private val messageDataStore: MessageDataStore,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel(), ChatViewModel {

    private val _messageList = MutableStateFlow<List<MessageDomainModel>>(emptyList())
    override val messageList: StateFlow<List<MessageDomainModel>> = _messageList

    private val _isModelResponding = MutableStateFlow(false)
    override val isModelResponding: StateFlow<Boolean> = _isModelResponding

    private var typingJob: Job? = null
    private var responseMs: Boolean = true

    init {
        loadMessages()
    }

    private fun loadMessages() {
        viewModelScope.launch {
            messageDataStore.getMessages.collect { messages ->
                _messageList.value = messages
            }
        }
    }

    override fun sendMessage(question: String, image: String?) {
        viewModelScope.launch {
            try {
                typingJob?.cancel()
                responseMs = true

                _messageList.value = _messageList.value + MessageDomainModel(question, "user")
                _isModelResponding.value = true

                val typingMessageIndex = _messageList.value.size
                _messageList.value = _messageList.value + MessageDomainModel("", "model")
                var isTyping = true
                val dots = listOf("", ".", "..")

                typingJob = launch {
                    var index = 0
                    while (isTyping) {
                        val animatedMessage = ".${dots[index % dots.size]}"
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
                    if (!responseMs) break
                    animatedResponse.append(char)
                    _messageList.value = _messageList.value.toMutableList().apply {
                        this[typingMessageIndex] = MessageDomainModel(animatedResponse.toString(), responseRole)
                    }
                    delay(20)
                }

                _isModelResponding.value = false
                _messageList.value = _messageList.value.dropLast(1) + MessageDomainModel(responseMessage, responseRole)
                messageDataStore.saveMessages(_messageList.value)

            } catch (e: Exception) {
                typingJob?.cancel()
                _isModelResponding.value = false
                val errorMessage = "Oops! Sepertinya ada kesalahan"
                if (_messageList.value.isNotEmpty()) {
                    _messageList.value = _messageList.value.dropLast(1) + MessageDomainModel(errorMessage, "model")
                } else {
                    _messageList.value = _messageList.value + MessageDomainModel(errorMessage, "model")
                }
                messageDataStore.saveMessages(_messageList.value)
            }
        }
    }

    override fun clearMessages() {
        viewModelScope.launch {
            typingJob?.cancel()
            _messageList.value = emptyList()
            messageDataStore.saveMessages(emptyList())
        }
    }

    override fun skipResponse() {
        responseMs = false
    }
}