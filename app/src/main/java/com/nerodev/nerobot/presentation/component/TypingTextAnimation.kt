package com.nerodev.nerobot.presentation.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun TypingTextAnimation(
    text: String,
    navController: NavController
) {
    val displayedText = remember { mutableStateOf("") }
    val typingSpeed = 100L

    val isChatScreen = remember {
        derivedStateOf { navController.currentBackStackEntry?.destination?.route == "chat" }
    }

    LaunchedEffect(key1 = isChatScreen.value) {
        if (isChatScreen.value) {
            for (i in text.indices) {
                displayedText.value = text.substring(0, i + 1)
                delay(typingSpeed)
            }
            delay(1000)
        }
    }

    Text(
        text = displayedText.value,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    )
}