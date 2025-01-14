package com.example.nerobot.presentation.component

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.nerobot.R

@Composable
fun MessageInput(
    onMessageSend: (String, String?) -> Unit,
    isModelResponding: Boolean,
    onCancelResponse: () -> Unit,
    modifier: Modifier = Modifier
) {
    var message by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { selectedImageUri ->
            imageUri = selectedImageUri?.toString()
        }
    )

    Column(modifier = modifier.padding(8.dp)) {
        imageUri?.let { uri ->
            val fileName = uri.substringAfterLast("/")
            ImageUriCard(fileName = fileName) {
                imageUri = null
            }
        }

        Row(
            modifier = Modifier
                .padding(top = 3.dp) // Ensure spacing between the URI text and the input field
                .heightIn(min = 56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextFieldComp(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier.weight(1f),
                placeholder = "Masukkan Perintahmu",
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_add_image),
                        contentDescription = "Attach Image",
                        modifier = Modifier
                            .size(28.dp)
                            .clickable {
                                launcher.launch("image/*")
                            }
                    )
                }
            )
            IconButton(onClick = {
                if (isModelResponding) {
                    onCancelResponse()
                } else {
                    if (message.isNotEmpty() || imageUri != null) {
                        onMessageSend(message, imageUri)
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
}