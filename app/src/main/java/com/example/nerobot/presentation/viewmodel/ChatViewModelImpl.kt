package com.example.nerobot.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
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
) : ViewModel(), ChatViewModel {

    private val _messageList = MutableStateFlow<List<MessageDomainModel>>(emptyList())
    override val messageList: StateFlow<List<MessageDomainModel>> = _messageList

    private val _isModelResponding = MutableStateFlow(false)
    override val isModelResponding: StateFlow<Boolean> = _isModelResponding

    private var typingJob: Job? = null
    private var messageLoadJob: Job? = null
    private var responseMs: Boolean = true

    init {
        loadMessages()
    }

    private fun loadMessages() {
        messageLoadJob?.cancel()
        messageLoadJob = viewModelScope.launch {
            messageDataStore.getMessages
                .collect { messages ->
                    Log.d("ChatViewModel", "Received messages update: ${messages.size} messages")
                    _messageList.value = messages
                }
        }
    }

    override fun sendMessage(question: String, image: String?) {
        viewModelScope.launch {
            try {
                typingJob?.cancel()
                responseMs = true

                // Update local state first
                val updatedList = _messageList.value + MessageDomainModel(question, "user", image)
                _messageList.value = updatedList

                // Save to DataStore immediately after user message
                messageDataStore.saveMessages(updatedList)
                Log.d("ChatViewModel", "Saved user message to DataStore")

                _isModelResponding.value = true
                val typingMessageIndex = _messageList.value.size

                // Add temporary typing message
                _messageList.value = _messageList.value + MessageDomainModel(".", "model")

                var isTyping = true
                val dots = listOf(".", "..", "...", "....")
                typingJob = launch {
                    var index = 0
                    while (isTyping) {
                        val animatedMessage = dots[index % dots.size]
                        _messageList.value = _messageList.value.toMutableList().apply {
                            this[typingMessageIndex] = MessageDomainModel(animatedMessage, "model")
                        }
                        index++
                        delay(300) // Delay untuk animasi
                    }
                }

                val response = sendMessageUseCase(_messageList.value, question, image)

                // Stop typing animation
                isTyping = false
                typingJob?.cancel()

                // Finalize response message
                val responseMessage = response.message
                val responseRole = response.role
                val responseImage = response.imageUrl
                val animatedResponse = StringBuilder()

                for (char in responseMessage) {
                    if (!responseMs) break
                    animatedResponse.append(char)
                    _messageList.value = _messageList.value.toMutableList().apply {
                        this[typingMessageIndex] = MessageDomainModel(animatedResponse.toString(), responseRole, responseImage)
                    }
                    delay(20) // Typing effect delay
                }

                _isModelResponding.value = false

                // Update final state and save to DataStore
                val finalList = _messageList.value.dropLast(1) + MessageDomainModel(responseMessage, responseRole, responseImage)
                _messageList.value = finalList
                messageDataStore.saveMessages(finalList)
                Log.d("ChatViewModel", "Saved final response to DataStore")

            } catch (e: Exception) {
                Log.e("ChatViewModel", "Error in sendMessage", e)
                handleError(e)
            }
        }
    }

    private fun handleError(e: Exception) {
        try {
            typingJob?.cancel()
            _isModelResponding.value = false
            val errorMessage = when (e) {
                is UnknownHostException -> "Tidak ada koneksi internet"
                is IllegalArgumentException -> "Format pesan tidak valid"
                else -> "Oops! Sepertinya terjadi kesalahan: ${e.message}"
            }

            val updatedList = if (_messageList.value.isNotEmpty()) {
                _messageList.value.dropLast(1) + MessageDomainModel(errorMessage, "model")
            } else {
                listOf(MessageDomainModel(errorMessage, "model"))
            }

            _messageList.value = updatedList
            viewModelScope.launch {
                messageDataStore.saveMessages(updatedList)
            }
        } catch (e: Exception) {
            Log.e("ChatViewModel", "Error handling error", e)
        }
    }

    override fun clearMessages() {
        viewModelScope.launch {
            _messageList.value = emptyList()
            messageDataStore.saveMessages(emptyList())
        }
    }

    override fun skipResponse() {
        responseMs = false
    }
}