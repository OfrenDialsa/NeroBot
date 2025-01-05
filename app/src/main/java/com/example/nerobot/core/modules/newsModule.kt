package com.example.nerobot.core.modules

import com.example.nerobot.data.api.ApiConfig
import com.example.nerobot.data.api.ApiService
import com.example.nerobot.data.repository.NewsRepositoryImpl
import com.example.nerobot.domain.repository.NewsRepository
import com.example.nerobot.domain.usecase.newsusecase.GetAllNewsUseCase
import com.example.nerobot.presentation.viewmodel.NewsViewModel
import com.example.nerobot.presentation.viewmodel.NewsViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newsModule = module {
    single<ApiService> { ApiConfig.getApiService() }
    single<NewsRepository> { NewsRepositoryImpl(get()) }
    single { GetAllNewsUseCase(get()) }
    viewModel<NewsViewModel> { NewsViewModelImpl(get()) }
}