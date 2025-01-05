package com.example.nerobot.domain.repository

import com.example.nerobot.core.utils.Result
import com.example.nerobot.data.model.NewsItem
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getAllNews(): Flow<Result<List<NewsItem>>>
}