package com.example.nerobot.core.modules

import com.example.nerobot.BuildConfig
import com.example.nerobot.data.local.MessageDataStore
import com.example.nerobot.data.repository.ChatRepositoryImpl
import com.example.nerobot.domain.repository.ChatRepository
import com.example.nerobot.domain.usecase.sendmessage.SendMessageUseCase
import com.example.nerobot.presentation.viewmodel.ChatViewModel
import com.example.nerobot.presentation.viewmodel.ChatViewModelImpl
import com.google.ai.client.generativeai.GenerativeModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val chatModule = module {
    single {
        GenerativeModel(
            modelName = "gemini-pro",
            apiKey = BuildConfig.API_KEY,
        )
    }

    single<ChatRepository> { ChatRepositoryImpl(get()) }
    single { MessageDataStore(get()) }
    factory { SendMessageUseCase(get()) }

    viewModel<ChatViewModelImpl> { ChatViewModelImpl(get(), get()) }
}