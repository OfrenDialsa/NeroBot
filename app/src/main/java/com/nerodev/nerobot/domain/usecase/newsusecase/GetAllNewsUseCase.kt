package com.nerodev.nerobot.domain.usecase.newsusecase

import com.nerodev.nerobot.core.utils.Result
import com.nerodev.nerobot.domain.model.NewsDomainModel
import com.nerodev.nerobot.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetAllNewsUseCase (private val repository: NewsRepository){
    suspend operator fun invoke() : Flow<Result<NewsDomainModel>> {
        return repository.getAllNews()
    }
}