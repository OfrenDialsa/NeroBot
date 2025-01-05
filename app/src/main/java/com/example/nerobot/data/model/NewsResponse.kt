package com.example.nerobot.data.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @field:SerializedName("totalResults")
	val totalResults: Int? = null,

    @field:SerializedName("articles")
	val articles: List<NewsItem> = emptyList(),

    @field:SerializedName("status")
	val status: String? = null
)

data class Source(
	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)