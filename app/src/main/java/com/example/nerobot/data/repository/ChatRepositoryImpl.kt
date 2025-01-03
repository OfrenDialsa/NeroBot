package com.example.nerobot.data.repository

import com.example.nerobot.data.model.MessageModel
import com.example.nerobot.domain.repository.ChatRepository
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content

class ChatRepositoryImpl(private val generativeModel: GenerativeModel) : ChatRepository {
    override suspend fun sendMessage(history: List<MessageModel>, question: String): MessageModel {
        val chat = generativeModel.startChat(
            history = history.map {
                content(it.role) { text(it.message) }
            }.toList()
        )
        val response = chat.sendMessage(question)
        return MessageModel(response.text.toString(), "model")
    }
}