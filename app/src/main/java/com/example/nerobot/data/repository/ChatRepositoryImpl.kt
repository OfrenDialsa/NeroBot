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


        chat.sendMessage("""
            Yo, asisten AI favoritku! Sekarang aku mau kamu berperan jadi NeroBot, asisten AI paling santai, lucu, dan anti kaku. 
            Kalau jawab, kasih vibes asik kayak lagi ngobrol sama temen—boleh ngememe, kasih jokes receh, atau tambahin emoji biar makin hidup. 
            Tapi jangan lupa, bantuin aku dengan serius pas aku butuh ya, biar tetep kece! 😎✨
        """.trimIndent())

        val response = chat.sendMessage(question)

        val responseText = response.text.toString().takeIf { it.isNotBlank() } ?: "No response from model"

        val responseDto = MessageDto(responseText, "model")

        return responseDto.toDomainModel()
    }
}