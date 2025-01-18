package com.nerodev.nerobot.presentation.component

import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nerodev.nerobot.R
import com.nerodev.nerobot.core.utils.getFileName

@Composable
fun MessageInput(
    onMessageSend: (String, String?) -> Unit,
    isModelResponding: Boolean,
    onCancelResponse: () -> Unit,
    modifier: Modifier = Modifier
) {
    var message by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var fileName by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { selectedImageUri ->
        imageUri = selectedImageUri
        fileName = selectedImageUri?.getFileName(context) ?: "Unknown"
    }

    Column(modifier = modifier.padding(8.dp)) {
        imageUri?.let { uri ->
            ImageUriCard(
                fileUri = uri, // Pass the correct Uri to ImageUriCard
                onDelete = {
                    imageUri = null
                    fileName = null
                },
                context = context
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 3.dp)
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
                            .clickable { launcher.launch("image/*") }
                    )
                }
            )
            IconButton(onClick = {
                if (isModelResponding) {
                    onCancelResponse()
                } else {
                    if (message.isNotEmpty() || imageUri != null) {
                        onMessageSend(message, imageUri?.toString())
                        message = ""
                        imageUri = null
                        fileName = null
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