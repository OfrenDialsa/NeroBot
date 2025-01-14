package com.example.nerobot.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.nerobot.presentation.viewmodel.ChatViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(navController = navController, startDestination = "chat", modifier = modifier) {
        chatNavigation(navController)
        newsNavigation(navController)
        savedNavigation(navController)
    }
}