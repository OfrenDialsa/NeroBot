package com.example.nerobot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.nerobot.core.theme.NeroBotTheme
import com.example.nerobot.presentation.screen.MainScreen
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
