package com.nerodev.nerobot.data.mapper

import com.nerodev.nerobot.data.model.NewsItem
import com.nerodev.nerobot.data.model.NewsResponse
import com.nerodev.nerobot.domain.model.ArticleDomainModel
import com.nerodev.nerobot.domain.model.NewsDomainModel

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