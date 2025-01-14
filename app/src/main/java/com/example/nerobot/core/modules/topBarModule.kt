package com.example.nerobot.core.modules

import com.example.nerobot.presentation.viewmodel.TopBarViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val topBarModule = module {
    viewModel { TopBarViewModelImpl() }
}