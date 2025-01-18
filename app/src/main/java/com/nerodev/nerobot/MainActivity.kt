package com.nerodev.nerobot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nerodev.nerobot.core.theme.NeroBotTheme
import com.nerodev.nerobot.presentation.screen.MainScreen
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NeroBotTheme {
                KoinContext {
                    MainScreen()
                }
            }
        }
    }
}
