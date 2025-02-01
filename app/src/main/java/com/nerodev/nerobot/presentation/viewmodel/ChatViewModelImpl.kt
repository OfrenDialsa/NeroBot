package com.nerodev.nerobot.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerodev.nerobot.data.local.MessageDataStore
import com.nerodev.nerobot.domain.model.MessageDomainModel
import com.nerodev.nerobot.domain.usecase.sendmessage.SendMessageUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class ChatViewModelImpl(
    private val messageDataStore: MessageDataStore,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel(), ChatViewModel {

    private val _messageList = MutableStateFlow<List<MessageDomainModel>>(emptyList())
    override val messageList: StateFlow<List<MessageDomainModel>> = _messageList

    private val _isModelResponding = MutableStateFlow(false)
    override val isModelResponding: StateFlow<Boolean> = _isModelResponding

    private val _message = MutableStateFlow("")
    override val message: StateFlow<String> = _message

    private val _imageUri = MutableStateFlow<Uri?>(null)
    override val imageUri: StateFlow<Uri?> = _imageUri

    private val _errorMessage = MutableStateFlow<String?>(null)
    override val errorMessage: StateFlow<String?> = _errorMessage

    private var typingJob: Job? = null
    private var responseMs = true
    private val mutex = Mutex()

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
            mutex.withLock {
                try {
                    typingJob?.cancel()
                    responseMs = true

                    _messageList.update { it + MessageDomainModel(question, "user") }
                    _isModelResponding.value = true

                    val typingMessageIndex = _messageList.value.size
                    _messageList.update { it + MessageDomainModel("", "model") }

                    typingJob = startTypingAnimation(typingMessageIndex)

                    val response = sendMessageUseCase(_messageList.value, question)

                    typingJob?.cancel()
                    _isModelResponding.value = false

                    updateResponseMessage(typingMessageIndex, response.message, response.role)
                    messageDataStore.saveMessages(_messageList.value)

                } catch (e: Exception) {
                    handleSendMessageError()
                }
            }
        }
    }

    private fun CoroutineScope.startTypingAnimation(index: Int) = launch {
        val dots = listOf("", ".", "..")
        var dotIndex = 0
        while (responseMs) {
            _messageList.update {
                it.toMutableList().apply {
                    this[index] = MessageDomainModel(".${dots[dotIndex % dots.size]}", "model")
                }
            }
            dotIndex++
            delay(200)
        }
    }

    private suspend fun updateResponseMessage(index: Int, message: String, role: String) {
        val animatedResponse = StringBuilder()
        for (char in message) {
            if (!responseMs) break
            animatedResponse.append(char)
            _messageList.update {
                it.toMutableList().apply {
                    this[index] = MessageDomainModel(animatedResponse.toString(), role)
                }
            }
            delay(20)
        }
        _messageList.update {
            it.dropLast(1) + MessageDomainModel(message, role)
        }
    }

    private fun handleSendMessageError() {
        typingJob?.cancel()
        _isModelResponding.value = false

        val errorMessage = "Oops! Sepertinya ada kesalahan"
        _messageList.update {
            if (it.isNotEmpty()) it.dropLast(1) + MessageDomainModel(errorMessage, "model")
            else it + MessageDomainModel(errorMessage, "model")
        }

        viewModelScope.launch {
            messageDataStore.saveMessages(_messageList.value)
        }
    }

    override fun onMessageChange(newMessage: String) {
        _message.value = newMessage
    }

    override fun onImageSelected(uri: Uri?, fileName: String?) {
        _imageUri.value = uri
    }

    override fun clearImage() {
        _imageUri.value = null
    }

    override fun setErrorMessage(error: String) {
        _errorMessage.value = error
    }

    override fun clearErrorMessage() {
        _errorMessage.value = null
    }

    override fun clearMessages() {
        viewModelScope.launch {
            mutex.withLock {
                typingJob?.cancel()
                _messageList.value = emptyList()
                messageDataStore.saveMessages(emptyList())
            }
        }
    }

    override fun skipResponse() {
        responseMs = false
    }
}