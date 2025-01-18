package com.nerodev.nerobot.domain.model

data class MessageDomainModel(
    val message : String,
    val role : String,
    val imageUrl: String? = null
)
