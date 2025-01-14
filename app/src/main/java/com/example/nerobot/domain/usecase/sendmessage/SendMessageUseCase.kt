package com.example.nerobot.domain.usecase.sendmessage

import com.example.nerobot.domain.model.MessageDomainModel
import com.example.nerobot.domain.repository.ChatRepository

class SendMessageUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        history: List<MessageDomainModel>, question: String,
        image: String? = null
    ): MessageDomainModel {
        return chatRepository.sendMessage(history, question, image)
    }
}