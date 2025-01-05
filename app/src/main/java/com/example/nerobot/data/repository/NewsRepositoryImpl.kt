package com.example.nerobot.data.repository

import com.example.nerobot.data.api.ApiService
import com.example.nerobot.data.model.NewsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.nerobot.core.utils.Result
import com.example.nerobot.domain.repository.NewsRepository

class NewsRepositoryImpl(private val apiService: ApiService) : NewsRepository {
    override fun getAllNews(): Flow<Result<List<NewsItem>>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getNews()
            if (response.status == "ok") {
                emit(Result.Success(response.articles))
            } else {
                emit(Result.Error("Error: ${response.status}"))
            }
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is retrofit2.HttpException -> "HTTP error: ${e.code()} - ${e.message()}"
                is java.net.UnknownHostException -> "Network error: Check your internet connection."
                else -> e.message ?: "Unknown error occurred"
            }
            emit(Result.Error(errorMessage))
        }
    }
}