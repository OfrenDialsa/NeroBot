package com.example.nerobot.data.api

import com.example.nerobot.BuildConfig
import com.example.nerobot.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines?country=us&category=technology&sortBy=publishedAt")
    suspend fun getNews(
        @Query("apiKey") apiKey: String= BuildConfig.NEWS_API_KEY,
        @Query("pageSize") pageSize: Int = 5)
    : NewsResponse
}