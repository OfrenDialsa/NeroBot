package com.nerodev.nerobot.core.modules

import com.nerodev.nerobot.data.api.ApiConfig
import com.nerodev.nerobot.data.api.ApiService
import com.nerodev.nerobot.data.repository.NewsRepositoryImpl
import com.nerodev.nerobot.domain.repository.NewsRepository
import com.nerodev.nerobot.domain.usecase.newsusecase.GetAllNewsUseCase
import com.nerodev.nerobot.presentation.viewmodel.NewsViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newsModule = module {
    single<ApiService> { ApiConfig.getApiService() }
    single<NewsRepository> { NewsRepositoryImpl(get()) }
    single { GetAllNewsUseCase(get()) }
    viewModel { NewsViewModelImpl(get()) }
}