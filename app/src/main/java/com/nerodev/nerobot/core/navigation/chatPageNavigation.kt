package com.nerodev.nerobot.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nerodev.nerobot.presentation.screen.chatpage.ChatPage

fun NavGraphBuilder.chatNavigation(navController: NavController) {
    composable("chat") {
        ChatPage(navController = navController)
    }
}