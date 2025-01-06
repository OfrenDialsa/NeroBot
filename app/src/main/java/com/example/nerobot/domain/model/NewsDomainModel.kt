package com.example.nerobot.domain.model

data class NewsDomainModel(
    val totalResults: Int,
    val articles: List<ArticleDomainModel>
)

