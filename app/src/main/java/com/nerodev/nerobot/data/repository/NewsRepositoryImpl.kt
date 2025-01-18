package com.nerodev.nerobot.data.repository

import com.nerodev.nerobot.core.utils.Result
import com.nerodev.nerobot.data.api.ApiService
import com.nerodev.nerobot.data.mapper.toDomainModel
import com.nerodev.nerobot.data.model.NewsResponse
import com.nerodev.nerobot.domain.model.NewsDomainModel
import com.nerodev.nerobot.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepositoryImpl(
    private val apiService: ApiService
) : NewsRepository {
    override fun getAllNews(): Flow<Result<NewsDomainModel>> = flow {
        emit(Result.Loading)
        try {
            val response: NewsResponse = apiService.getNews()
            if (response.status == "ok") {
                val domainNews = response.toDomainModel()
                emit(Result.Success(domainNews))
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