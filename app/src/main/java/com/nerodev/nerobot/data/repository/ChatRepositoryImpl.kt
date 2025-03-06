package com.nerodev.nerobot.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.nerodev.nerobot.data.mapper.toDomainModel
import com.nerodev.nerobot.data.model.MessageDto
import com.nerodev.nerobot.domain.model.MessageDomainModel
import com.nerodev.nerobot.domain.repository.ChatRepository
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