package com.example.nerobot.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.nerobot.core.utils.Result
import com.example.nerobot.data.model.NewsItem
import kotlinx.coroutines.flow.StateFlow

abstract class NewsViewModel : ViewModel() {
    abstract val news : StateFlow<Result<List<NewsItem>>>
    abstract fun getAllNews()
}