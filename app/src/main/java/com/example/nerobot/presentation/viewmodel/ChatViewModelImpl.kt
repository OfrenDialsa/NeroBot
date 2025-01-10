package com.example.nerobot.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.nerobot.core.utils.removeAsterisks
import com.example.nerobot.data.local.MessageDataStore
import com.example.nerobot.domain.model.MessageDomainModel
import com.example.nerobot.domain.usecase.sendmessage.SendMessageUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class ChatViewModelImpl(
    private val sendMessageUseCase: SendMessageUseCase,
    private val messageDataStore: MessageDataStore
) : ChatViewModel() {

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
            _messageList.value = messageDataStore.getMessages.first()
        }
    }

    override fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                typingJob?.cancel()
                responseMs = true

                _messageList.value = _messageList.value + MessageDomainModel(question, "user")

                _isModelResponding.value = true

                val typingMessageIndex = _messageList.value.size

                _messageList.value = _messageList.value + MessageDomainModel("Loading.", "model")
                var isTyping = true
                val dots = listOf(".", "..", "...", "....")
                typingJob = launch {
                    var index = 0
                    while (isTyping) {
                        val animatedMessage = "Loading${dots[index % dots.size]}"
                        _messageList.value = _messageList.value.toMutableList().apply {
                            this[typingMessageIndex] = MessageDomainModel(animatedMessage, "model")
                        }
                        index++
                        delay(200)
                    }
                }

                val response = sendMessageUseCase(_messageList.value, question)

                isTyping = false

                val responseMessage = response.message
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
                try {
                    typingJob?.cancel()
                    _isModelResponding.value = false
                    val errorMessage = "Oops! Sepertinya terjadi kesalahan"

                    if (_messageList.value.isNotEmpty()) {
                        _messageList.value = _messageList.value.dropLast(1) + MessageDomainModel(errorMessage, "model")
                    } else {
                        _messageList.value = _messageList.value + MessageDomainModel(errorMessage, "model")
                    }
                } catch (e: Exception) {
                    typingJob?.cancel()
                    _isModelResponding.value = false

                    val errorMessage = when (e) {
                        is UnknownHostException -> "Tidak ada koneksi internet"
                        is IllegalArgumentException -> "Format pesan tidak valid"
                        else -> "Oops! Terjadi kesalahan: ${e.message}"
                    }

                    // Log error untuk debugging
                    Log.e("ChatViewModel", "Error sending message", e)

                    if (_messageList.value.isNotEmpty()) {
                        _messageList.value = _messageList.value.dropLast(1) + MessageDomainModel(errorMessage, "model")
                    } else {
                        _messageList.value = _messageList.value + MessageDomainModel(errorMessage, "model")
                    }
                }

            }
        }
    }

    override fun clearMessages() {
        _messageList.value = emptyList()
        viewModelScope.launch {
            messageDataStore.saveMessages(emptyList())
        }
    }

    override fun skipResponse() {
        responseMs = false
    }
}