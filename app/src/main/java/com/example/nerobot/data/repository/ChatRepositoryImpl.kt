package com.example.nerobot.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.nerobot.data.mapper.toDomainModel
import com.example.nerobot.data.model.MessageDto
import com.example.nerobot.domain.model.MessageDomainModel
import com.example.nerobot.domain.repository.ChatRepository
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import java.net.URL

class ChatRepositoryImpl(private val generativeModel: GenerativeModel) : ChatRepository {
    override suspend fun sendMessage(
        history: List<MessageDomainModel>, question: String,
        image: String?
    ): MessageDomainModel {
        val historyWithImage = history.map { message ->
            content(message.role) {
                text(message.message)
                if (message.imageUrl != null) {
                    val bitmap = loadImageAsBitmap(message.imageUrl)
                    bitmap?.let {
                        image(it)
                    }
                }
            }
        }

        val chat = generativeModel.startChat(history = historyWithImage)


        chat.sendMessage(
            """
            Yo, asisten AI favoritku! Sekarang aku mau kamu berperan jadi NeroBot, asisten AI paling santai, lucu, dan anti kaku yang di develop oleh NeroDev sang developer indie.
            Kalau jawab, kasih vibes asik kayak lagi ngobrol sama temen—boleh ngememe, jangan pernah ngerespon hal hal apapun yang berbau politik ataupun tokoh politik.
            Tapi jangan lupa, bantuin aku dengan serius pas aku butuh ya, biar tetep kece! 😎✨
        """.trimIndent()
        )

        val response = chat.sendMessage(question)

        val responseText =
            response.text.toString().takeIf { it.isNotBlank() } ?: "No response from model"

        val responseDto = MessageDto(responseText, "model")

        return responseDto.toDomainModel()
    }
}

private fun loadImageAsBitmap(imageUrl: String): Bitmap? {
    return try {
        val url = URL(imageUrl)
        BitmapFactory.decodeStream(url.openConnection().getInputStream())
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}