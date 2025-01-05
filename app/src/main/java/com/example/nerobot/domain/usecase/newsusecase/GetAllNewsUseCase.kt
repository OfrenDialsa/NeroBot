package com.example.nerobot.domain.usecase.newsusecase

import com.example.nerobot.data.model.NewsItem
import com.example.nerobot.core.utils.Result
import com.example.nerobot.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetAllNewsUseCase (private val repository: NewsRepository){
    suspend operator fun invoke() : Flow<Result<List<NewsItem>>>{
        return repository.getAllNews()
    }
}