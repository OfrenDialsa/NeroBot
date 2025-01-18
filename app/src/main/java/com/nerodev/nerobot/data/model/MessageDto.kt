package com.nerodev.nerobot.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageDto(
    val message: String,
    val role: String,
    val imageUrl: String? = null
)