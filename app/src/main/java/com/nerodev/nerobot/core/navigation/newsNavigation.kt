package com.nerodev.nerobot.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nerodev.nerobot.presentation.screen.newspage.NewsPage

fun NavGraphBuilder.newsNavigation(navController: NavController) {
    composable("news") {
        NewsPage(navController = navController)
    }
}