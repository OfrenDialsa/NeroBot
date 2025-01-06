package com.example.nerobot.domain.usecase.newsusecase

import com.example.nerobot.data.model.NewsItem
import com.example.nerobot.core.utils.Result
import com.example.nerobot.domain.model.ArticleDomainModel
import com.example.nerobot.domain.model.NewsDomainModel
import com.example.nerobot.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetAllNewsUseCase (private val repository: NewsRepository){
    suspend operator fun invoke() : Flow<Result<NewsDomainModel>> {
        return repository.getAllNews()
    }
}