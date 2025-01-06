package com.example.nerobot.data.mapper

import com.example.nerobot.data.model.NewsItem
import com.example.nerobot.data.model.NewsResponse
import com.example.nerobot.domain.model.ArticleDomainModel
import com.example.nerobot.domain.model.NewsDomainModel

fun NewsResponse.toDomainModel(): NewsDomainModel {
    return NewsDomainModel(
        totalResults = totalResults ?: 0,
        articles = articles.map { it.toDomainModel() }
    )
}

fun NewsItem.toDomainModel(): ArticleDomainModel {
    return ArticleDomainModel(
        title = title.orEmpty(),
        description = description.orEmpty(),
        url = url.orEmpty(),
        urlToImage = urlToImage,
        author = author.orEmpty(),
        sourceName = source?.name.orEmpty(),
        publishedAt = publishedAt.orEmpty()
    )
}