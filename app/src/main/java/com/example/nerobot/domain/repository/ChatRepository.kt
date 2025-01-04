package com.example.nerobot.domain.repository

import com.example.nerobot.domain.model.MessageDomainModel

interface ChatRepository {
    suspend fun sendMessage(history: List<MessageDomainModel>, question: String): MessageDomainModel
}