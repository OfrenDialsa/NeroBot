package com.example.nerobot.core.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.nerobot.presentation.screen.chatpage.ChatPage
import com.example.nerobot.presentation.viewmodel.ChatViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.chatNavigation(navController: NavController) {
    composable("chat") {
        ChatPage(navController = navController)
    }
}