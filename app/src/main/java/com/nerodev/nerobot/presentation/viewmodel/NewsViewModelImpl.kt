package com.nerodev.nerobot.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerodev.nerobot.domain.usecase.newsusecase.GetAllNewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.nerodev.nerobot.core.utils.Result
import com.nerodev.nerobot.domain.model.NewsDomainModel


class NewsViewModelImpl(private val newsUseCase: GetAllNewsUseCase) : ViewModel(), NewsViewModel {

    private val _news = MutableStateFlow<Result<NewsDomainModel>>(Result.Loading)
    override val news: StateFlow<Result<NewsDomainModel>> get() = _news

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