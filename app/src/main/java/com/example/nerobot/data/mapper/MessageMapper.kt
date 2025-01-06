package com.example.nerobot.data.mapper

import com.example.nerobot.data.model.MessageDto
import com.example.nerobot.domain.model.MessageDomainModel

fun MessageDto.toDomainModel(): MessageDomainModel {
    return MessageDomainModel(
        message = this.message,
        role = this.role
    )
}
