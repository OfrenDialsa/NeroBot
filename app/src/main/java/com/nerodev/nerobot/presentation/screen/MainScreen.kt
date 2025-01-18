package com.nerodev.nerobot.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nerodev.nerobot.core.navigation.AppNavigation
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(240.dp)
            ) {
                DrawerContent(
                    selectedItem = currentRoute ?: "chat",
                    onItemSelected = { item ->
                        scope.launch { drawerState.close() }
                        if (currentRoute != item) {
                            navController.navigate(item) {
                                launchSingleTop = true
                                popUpTo(navController.graph.startDestinationId) { inclusive = false }
                            }
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            modifier = modifier
                .fillMaxSize(),
            topBar = {
                NeroBotTopAppBar(
                    onOpenDrawer = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    },
                    currentRoute = currentRoute ?: "",
                )
            },
            bottomBar = {

            }
        ) { padding ->
            AppNavigation(
                navController = navController,
                modifier = modifier
                    .padding(padding)
            )
        }
    }
}