package com.nerodev.nerobot.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nerodev.nerobot.presentation.screen.savedpage.SavedPage

fun NavGraphBuilder.savedNavigation(navController: NavController) {
    composable("saved") {
        SavedPage(navController = navController)
    }
}