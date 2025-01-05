package com.example.nerobot.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nerobot.data.model.NewsItem
import com.example.nerobot.domain.usecase.newsusecase.GetAllNewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.nerobot.core.utils.Result


class NewsViewModelImpl(private val newsUseCase: GetAllNewsUseCase) : NewsViewModel() {

    private val _news = MutableStateFlow<Result<List<NewsItem>>>(Result.Loading)
    override val news: StateFlow<Result<List<NewsItem>>> = _news

    init {
        getAllNews()
    }

    override fun getAllNews() {
        viewModelScope.launch {
            newsUseCase().collect { result ->
                when (result) {
                    is Result.Error -> {
                        _news.value = Result.Error(result.exception)
                    }
                    is Result.Loading -> {
                        _news.value = Result.Loading
                    }
                    is Result.Success -> {
                        _news.value = Result.Success(result.data)
                    }
                }
            }
        }
    }
}