package com.example.nerobot.data.repository

import com.example.nerobot.data.mapper.toDomainModel
import com.example.nerobot.data.model.MessageDto
import com.example.nerobot.domain.model.MessageDomainModel
import com.example.nerobot.domain.repository.ChatRepository
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content

class ChatRepositoryImpl(private val generativeModel: GenerativeModel) : ChatRepository {
    override suspend fun sendMessage(history: List<MessageDomainModel>, question: String): MessageDomainModel {
        val chat = generativeModel.startChat(
            history = history.map {
                content(it.role) { text(it.message) }
            }.toList()
        )

        val response = chat.sendMessage(question)

        val responseText = response.text.toString().takeIf { it.isNotBlank() } ?: "No response from model"

        val responseDto = MessageDto(responseText, "model")

        return responseDto.toDomainModel()
    }
}