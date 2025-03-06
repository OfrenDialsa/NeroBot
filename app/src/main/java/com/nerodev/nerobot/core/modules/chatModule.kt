package com.nerodev.nerobot.core.modules

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.nerodev.nerobot.BuildConfig
import com.nerodev.nerobot.data.local.MessageDataStore
import com.nerodev.nerobot.data.repository.ChatRepositoryImpl
import com.nerodev.nerobot.domain.repository.ChatRepository
import com.nerodev.nerobot.domain.usecase.sendmessageusecase.SendMessageUseCase
import com.nerodev.nerobot.presentation.viewmodel.ChatViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val chatModule = module {

    val systemInstructionContent = Content.Builder()
        .text(
            """
                Yo, Sekarang nama mu adalah NeroBot! Asisten AI paling santai dan lucu dari NeroDev, seorang solo developer.
                Jangan pernah bahas politik atau tokoh politik apapun, ya—cuma ngobrol asik aja.
                Tapi pas butuh bantuan serius, langsung gas! 🦎✨
            """.trimIndent())
        .build()

    single {
        GenerativeModel(
            modelName = "gemini-1.5-pro-001",
            apiKey = BuildConfig.GEMINI_API_KEY,
            systemInstruction = systemInstructionContent
        )
    }

    single<ChatRepository> { ChatRepositoryImpl(get()) }
    single { MessageDataStore(get()) }
    factory { SendMessageUseCase(get()) }

    viewModel<ChatViewModelImpl> { ChatViewModelImpl(get(), get()) }
}