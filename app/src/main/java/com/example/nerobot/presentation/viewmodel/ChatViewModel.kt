package com.example.nerobot.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.nerobot.data.model.MessageModel

abstract class ChatViewModel : ViewModel() {
    abstract val messageList: MutableList<MessageModel>
    abstract fun sendMessage(question: String)
}