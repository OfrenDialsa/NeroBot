package com.example.nerobot.presentation.screen.chatpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nerobot.core.theme.NeroBotColor
import com.example.nerobot.presentation.component.HorizontalDividerComp
import com.example.nerobot.presentation.viewmodel.ChatViewModel
import kotlinx.coroutines.Delay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatPageTopAppBar(
    viewModel: ChatViewModel = koinViewModel()
) {
    var expanded = remember { mutableStateOf(false) }
    var showClearChatDialog = remember { mutableStateOf(false) }
    var showEmptyMessageDialog = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        // TopAppBar
        TopAppBar(
            title = {
                Text(
                    text = buildAnnotatedString {
                        append("NeroBot")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = NeroBotColor.KellyGreen)) {
                            append("  ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, fontSize = 12.sp)) {
                            append("Powered By Gemini API")
                        }
                    },
                    color = NeroBotColor.KellyGreen,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu"
                    )
                }
            },
            actions = {
                IconButton(onClick = { expanded.value = !expanded.value }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "More Options"
                    )
                }
            },
        )

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier
                .align(Alignment.TopEnd),
            shape = MaterialTheme.shapes.medium,
            offset = DpOffset(x = (-12).dp, y = 0.dp)  // Adjust the x offset to fine-tune the position
        ) {
            DropdownMenuItem(
                text = { Text("New Chat") },
                onClick = {

                }
            )

            DropdownMenuItem(
                text = { Text("Clear Chat") },
                onClick = {
                    expanded.value = false
                    if (viewModel.messageList.value.isEmpty()) {
                        showEmptyMessageDialog.value = true
                    } else {
                        showClearChatDialog.value = true
                    }
                }
            )
        }

        if (showClearChatDialog.value) {
            androidx.compose.material3.AlertDialog(
                onDismissRequest = { showClearChatDialog.value = false },
                title = { Text("Clear Chat") },
                text = { Text("Are you sure to clear the current chat?") },
                confirmButton = {
                    androidx.compose.material3.TextButton(
                        onClick = {
                            showClearChatDialog.value = false
                            viewModel.clearMessages()
                        }
                    ) {
                        Text(text = "Yes", color = NeroBotColor.KellyGreen)
                    }
                },
                dismissButton = {
                    androidx.compose.material3.TextButton(
                        onClick = { showClearChatDialog.value = false }
                    ) {
                        Text("No", color = NeroBotColor.KellyGreen)
                    }
                }
            )
        }

        // Empty message dialog
        if (showEmptyMessageDialog.value) {
            androidx.compose.material3.AlertDialog(
                onDismissRequest = { showEmptyMessageDialog.value = false },
                title = { Text("Empty Chat") },
                text = { Text("There are no messages to clear.") },
                confirmButton = {
                    androidx.compose.material3.TextButton(
                        onClick = { showEmptyMessageDialog.value = false }
                    ) {
                        Text("OK", color = NeroBotColor.KellyGreen)
                    }
                }
            )
        }
    }
}