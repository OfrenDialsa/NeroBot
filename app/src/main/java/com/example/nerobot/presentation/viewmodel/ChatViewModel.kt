package com.example.nerobot.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.nerobot.data.model.MessageModel
import kotlinx.coroutines.flow.StateFlow

abstract class ChatViewModel : ViewModel() {
    abstract val messageList: StateFlow<List<MessageModel>>
    abstract fun sendMessage(question: String)
}