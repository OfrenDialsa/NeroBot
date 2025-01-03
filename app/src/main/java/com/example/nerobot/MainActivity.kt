package com.example.nerobot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.nerobot.core.theme.NeroBotTheme
import com.example.nerobot.presentation.screen.chatpage.ChatPage
import com.example.nerobot.presentation.viewmodel.ChatViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {

    private val chatViewModel: ChatViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NeroBotTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ChatPage(modifier = Modifier.padding(innerPadding), viewModel = chatViewModel)
                }
            }
        }
    }
}