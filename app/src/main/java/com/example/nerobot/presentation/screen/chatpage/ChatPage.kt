package com.example.nerobot.presentation.screen.chatpage

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nerobot.presentation.component.MessageInput
import com.example.nerobot.presentation.component.MessageList
import com.example.nerobot.presentation.component.ScaffoldComp
import com.example.nerobot.presentation.viewmodel.ChatViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatPage(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = koinViewModel()
) {
    val messages = viewModel.messageList.collectAsState(initial = emptyList())

    ScaffoldComp(
        topBar = {
            Column {
                ChatPageTopAppBar()
            }
        },
        content = {
            Column {
                MessageList(modifier = Modifier.weight(1f), messageList = messages.value)
                MessageInput(onMessageSend = { viewModel.sendMessage(it) })
            }
        },
        bottomBar = {

        },
        modifier = modifier
    )
}

@Preview
@Composable
fun ChatPagePreview(){
    ChatPage()
}





