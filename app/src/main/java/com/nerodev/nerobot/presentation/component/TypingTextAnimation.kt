package com.nerodev.nerobot.presentation.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    val fullText = text
    val displayedText = remember { mutableStateOf("") }
    val typingSpeed = 100L
    val deletingSpeed = 50L

    val currentRoute = navController.currentBackStackEntry?.destination?.route

    if (currentRoute == "chat") {
        LaunchedEffect(key1 = true) {
            while (true) {
                // Typing effect
                for (i in fullText.indices) {
                    displayedText.value = fullText.substring(0, i + 1)
                    delay(typingSpeed)
                }

                delay(1000)

                for (i in fullText.length downTo 1) {
                    displayedText.value = fullText.substring(0, i - 1)
                    delay(deletingSpeed)
                }

                delay(500)
            }
        }
    }

    Text(
        text = displayedText.value,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    )
}