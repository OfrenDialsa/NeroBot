package com.nerodev.nerobot.domain.usecase.sendmessage

import com.nerodev.nerobot.domain.model.MessageDomainModel
import com.nerodev.nerobot.domain.repository.ChatRepository

class SendMessageUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        history: List<MessageDomainModel>, question: String,
        image: String? = null
    ): MessageDomainModel {
        return chatRepository.sendMessage(history, question, image)
    }
}