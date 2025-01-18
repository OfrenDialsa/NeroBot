package com.nerodev.nerobot.data.mapper

import com.nerodev.nerobot.data.model.MessageDto
import com.nerodev.nerobot.domain.model.MessageDomainModel

fun MessageDto.toDomainModel(): MessageDomainModel {
    return MessageDomainModel(
        message = this.message,
        role = this.role
    )
}
