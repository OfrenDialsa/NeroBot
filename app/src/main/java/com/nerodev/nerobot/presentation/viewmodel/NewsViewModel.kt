package com.nerodev.nerobot.presentation.viewmodel

import com.nerodev.nerobot.core.utils.Result
import com.nerodev.nerobot.domain.model.NewsDomainModel
import kotlinx.coroutines.flow.StateFlow

interface NewsViewModel {
    val news : StateFlow<Result<NewsDomainModel>>
    fun getAllNews()
}