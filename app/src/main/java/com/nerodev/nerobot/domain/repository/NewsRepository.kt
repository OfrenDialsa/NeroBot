package com.nerodev.nerobot.domain.repository

import com.nerodev.nerobot.core.utils.Result
import com.nerodev.nerobot.domain.model.NewsDomainModel
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getAllNews(): Flow<Result<NewsDomainModel>>
}