package com.example.nerobot.core.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val title: String,
    val selectedIcon: String,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)
