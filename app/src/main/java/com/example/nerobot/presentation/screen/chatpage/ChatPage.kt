package com.example.nerobot.presentation.screen.chatpage

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.nerobot.presentation.component.MessageInput
import com.example.nerobot.presentation.component.MessageList
import com.example.nerobot.presentation.viewmodel.ChatViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ChatViewModel = koinViewModel()
) {
    val messages = viewModel.messageList.collectAsState(initial = emptyList())
    val isModelResponding = viewModel.isModelResponding.collectAsState(initial = false)

    Column {
        MessageList(modifier = modifier.weight(1f), messageList = messages.value, navController)
        MessageInput(
            onMessageSend = { viewModel.sendMessage(it) }, isModelResponding.value,
            onCancelResponse = { viewModel.skipResponse() }
        )
    }
}

//@Preview
//@Composable
//fun ChatPagePreview(){
//    ChatPage()
//}





