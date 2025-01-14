package com.example.nerobot.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nerobot.core.theme.NeroBotColor
import com.example.nerobot.presentation.viewmodel.ChatViewModelImpl
import com.example.nerobot.presentation.viewmodel.TopBarViewModelImpl
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeroBotTopAppBar(
    onOpenDrawer: () -> Unit,
    currentRoute: String,
    modifier: Modifier = Modifier
) {
    val viewModel: ChatViewModelImpl = koinViewModel()
    val topBarViewModel: TopBarViewModelImpl = koinViewModel()
    val expanded = topBarViewModel.isDropdownExpanded.collectAsState().value
    val showClearChatDialog = topBarViewModel.showClearChatDialog.collectAsState().value
    val showEmptyMessageDialog = topBarViewModel.showEmptyMessageDialog.collectAsState().value

    val context = LocalContext.current

    Box(modifier = modifier
        .fillMaxWidth()
    ) {
        TopAppBar(
            modifier = Modifier,
            title = {
                val titleText = when (currentRoute) {
                    "chat" -> "Chat"
                    "saved" -> "Saved"
                    "news" -> "Latest News"
                    else -> "NeroBot"
                }

                Text(
                    text = buildAnnotatedString {
                        append("NeroBot")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = NeroBotColor.KellyGreen,
                            fontSize = 26.sp)) {
                            append("  ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, fontSize = 12.sp)) {
                            append("-  $titleText")
                        }
                    },
                    color = NeroBotColor.KellyGreen,
                    fontWeight = FontWeight.Bold,
                )
            },
            navigationIcon = {
                IconButton(onClick = onOpenDrawer) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                }
            },
            actions = {
                if (currentRoute == "chat") {
                    IconButton(onClick = { topBarViewModel.toggleDropdown() }) {
                        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "More Options")
                    }
                }
            },

        )

        if (currentRoute == "chat") {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { topBarViewModel.dismissDropdown() },
                modifier = Modifier.align(Alignment.TopEnd),
                shape = MaterialTheme.shapes.medium,
                offset = DpOffset(x = (-12).dp, y = 0.dp)
            ) {
                DropdownMenuItem(
                    text = { Text("Save Conversation") },
                    onClick = {
                        topBarViewModel.dismissDropdown()
                        Toast.makeText(
                            context,
                            "Belum di develop cik, Nunggu niat awkoakw",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )

                DropdownMenuItem(
                    text = { Text("Clear Chat") },
                    onClick = {
                        topBarViewModel.dismissDropdown()
                        if (viewModel.messageList.value.isEmpty()) {
                            topBarViewModel.showEmptyMessageDialog()
                        } else {
                            topBarViewModel.showClearChatDialog()
                        }
                    }
                )
            }
        }

        if (showClearChatDialog) {
            AlertDialog(
                onDismissRequest = { topBarViewModel.dismissClearChatDialog() },
                title = { Text("Clear Chat") },
                text = { Text("Are you sure to clear the current chat?") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.clearMessages()
                        topBarViewModel.dismissClearChatDialog()
                    }) {
                        Text(text = "Yes", color = NeroBotColor.KellyGreen)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { topBarViewModel.dismissClearChatDialog() }) {
                        Text("No", color = NeroBotColor.KellyGreen)
                    }
                }
            )
        }

        if (showEmptyMessageDialog) {
            AlertDialog(
                onDismissRequest = { topBarViewModel.dismissEmptyMessageDialog() },
                title = { Text("Empty Chat") },
                text = { Text("There are no messages to clear.") },
                confirmButton = {
                    TextButton(onClick = { topBarViewModel.dismissEmptyMessageDialog() }) {
                        Text("OK", color = NeroBotColor.KellyGreen)
                    }
                }
            )
        }
    }
}