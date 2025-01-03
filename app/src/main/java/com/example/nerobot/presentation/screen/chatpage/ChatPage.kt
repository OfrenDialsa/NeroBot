package com.example.nerobot.presentation.screen.chatpage

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nerobot.presentation.component.MessageInput
import com.example.nerobot.presentation.component.MessageList
import com.example.nerobot.presentation.screen.AppHeader
import com.example.nerobot.presentation.viewmodel.ChatViewModel

@Composable
fun ChatPage(modifier: Modifier = Modifier, viewModel: ChatViewModel) {
    Column(modifier = modifier) {
        AppHeader()
        MessageList(modifier = Modifier.weight(1f), messageList = viewModel.messageList)
        MessageInput(onMessageSend = {
            viewModel.sendMessage(it)
        })
    }
}





