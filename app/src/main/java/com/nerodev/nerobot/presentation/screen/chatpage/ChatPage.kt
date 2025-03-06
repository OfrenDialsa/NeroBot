package com.nerodev.nerobot.presentation.screen.chatpage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nerodev.nerobot.presentation.component.MessageInput
import com.nerodev.nerobot.presentation.component.MessageList
import com.nerodev.nerobot.presentation.component.NoConnectionAlert
import com.nerodev.nerobot.presentation.viewmodel.ChatViewModelImpl
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatPage(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val viewModel: ChatViewModelImpl = koinViewModel()
    val isConnected = viewModel.isConnected.collectAsState(initial = true)
    val isModelResponding = viewModel.isModelResponding.collectAsState(initial = false)
    val messages = viewModel.messageList.collectAsState(initial = emptyList())

    Column {
        MessageList(
            modifier = modifier.weight(1f),
            messageList = messages.value,
            navController = navController
        )
        if (isConnected.value) {
            MessageInput(
                onMessageSend = { text, image -> viewModel.sendMessage(text, image) },
                isModelResponding = isModelResponding.value,
                onCancelResponse = { viewModel.skipResponse() }
            )
        } else{
            NoConnectionAlert(
                modifier = modifier.padding(bottom = 8.dp)
            )
        }
    }
}
