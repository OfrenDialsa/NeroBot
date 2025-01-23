package com.nerodev.nerobot.core.modules

import com.nerodev.nerobot.BuildConfig
import com.nerodev.nerobot.data.local.MessageDataStore
import com.nerodev.nerobot.data.repository.ChatRepositoryImpl
import com.nerodev.nerobot.domain.repository.ChatRepository
import com.nerodev.nerobot.domain.usecase.sendmessage.SendMessageUseCase
import com.nerodev.nerobot.presentation.viewmodel.ChatViewModelImpl
import com.google.ai.client.generativeai.GenerativeModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val chatModule = module {
    single {
        GenerativeModel(
            modelName = "gemini-pro",
            apiKey = BuildConfig.GEMINI_API_KEY,
        )
    }

    single<ChatRepository> { ChatRepositoryImpl(get()) }
    single { MessageDataStore(get()) }
    factory { SendMessageUseCase(get()) }

    viewModel<ChatViewModelImpl> { ChatViewModelImpl(get(), get()) }
}