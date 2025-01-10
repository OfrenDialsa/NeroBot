package com.example.nerobot.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import com.example.nerobot.R
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MessageInput(
    onMessageSend: (String) -> Unit,
    isModelResponding: Boolean,
    onCancelResponse: () -> Unit,
    modifier: Modifier = Modifier
) {
    var message by remember {
        mutableStateOf("")
    }

    Row(
        modifier = modifier
            .padding(start = 16.dp, bottom = 12.dp, end = 4.dp, top = 4.dp)
            .heightIn(min = 56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextFieldComp(
            value = message,
            onValueChange = { message = it },
            modifier = Modifier
                .weight(1f),
            placeholder = "Masukkan Perintahmu"
        )
        IconButton(onClick = {
            if (isModelResponding) {
                onCancelResponse()
            } else {
                if (message.isNotEmpty()) {
                    onMessageSend(message)
                    message = ""
                }
            }
        }) {
            Icon(
                painter = painterResource(if (isModelResponding) R.drawable.ic_skip else R.drawable.ic_send),
                contentDescription = if (isModelResponding) "Skip" else "Send",
                modifier = Modifier.size(48.dp)
            )
        }
    }
}