package com.example.nerobot.domain.repository

import com.example.nerobot.data.model.MessageModel

interface ChatRepository {
    suspend fun sendMessage(history: List<MessageModel>, question: String): MessageModel
}