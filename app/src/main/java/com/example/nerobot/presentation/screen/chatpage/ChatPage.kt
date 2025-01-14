package com.example.nerobot.presentation.screen.chatpage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.nerobot.presentation.component.MessageInput
import com.example.nerobot.presentation.component.MessageList
import com.example.nerobot.presentation.viewmodel.ChatViewModel
import com.example.nerobot.presentation.viewmodel.ChatViewModelImpl
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatPage(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val viewModel: ChatViewModelImpl = koinViewModel()

    val isModelResponding = viewModel.isModelResponding.collectAsState(initial = false)
    val messages = viewModel.messageList.collectAsState(initial = emptyList())

    Column {
        MessageList(
            modifier = modifier.weight(1f),
            messageList = messages.value,
            navController = navController
        )
        MessageInput(
            onMessageSend = { text, image -> viewModel.sendMessage(text, image) },
            isModelResponding = isModelResponding.value,
            onCancelResponse = { viewModel.skipResponse() }
        )
    }
}






