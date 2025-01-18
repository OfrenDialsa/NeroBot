package com.nerodev.nerobot.domain.model

data class ArticleDomainModel(
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String?,
    val author: String?,
    val sourceName: String,
    val publishedAt: String
)