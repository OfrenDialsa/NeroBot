package com.nerodev.nerobot.domain.repository

import com.nerodev.nerobot.domain.model.MessageDomainModel

interface ChatRepository {
    suspend fun sendMessage(history: List<MessageDomainModel>, question: String, image: String?): MessageDomainModel
}