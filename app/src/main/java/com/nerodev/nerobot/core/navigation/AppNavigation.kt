package com.nerodev.nerobot.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

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