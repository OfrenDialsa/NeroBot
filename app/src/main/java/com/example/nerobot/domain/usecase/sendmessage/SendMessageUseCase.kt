package com.example.nerobot.domain.usecase.sendmessage

import com.example.nerobot.data.model.MessageModel
import com.example.nerobot.domain.repository.ChatRepository

class SendMessageUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(history: List<MessageModel>, question: String): MessageModel {
        return chatRepository.sendMessage(history, question)
    }
}