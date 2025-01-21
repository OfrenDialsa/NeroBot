package com.nerodev.nerobot.presentation.component

import android.net.Uri
import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nerodev.nerobot.R
import com.nerodev.nerobot.core.utils.getFileName
import com.nerodev.nerobot.core.utils.getFileSize
import com.nerodev.nerobot.presentation.viewmodel.ChatViewModelImpl
import org.koin.androidx.compose.koinViewModel

@Composable
fun MessageInput(
    onMessageSend: (String, String?) -> Unit,
    isModelResponding: Boolean,
    onCancelResponse: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChatViewModelImpl = koinViewModel()
) {
    val message = viewModel.message.collectAsState().value
    val imageUri = viewModel.imageUri.collectAsState().value
    val errorMessage = viewModel.errorMessage.collectAsState().value

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            val mimeType = context.contentResolver.getType(uri) ?: ""
            if (mimeType.startsWith("image/")) {
                val selectedFileName = uri.getFileName(context) ?: "Unknown File"
                val fileSize = uri.getFileSize(context)

                if (fileSize > 10 * 1024 * 1024) {
                    viewModel.setErrorMessage("File cannot exceed 10 MB.")
                } else {
                    viewModel.onImageSelected(uri, selectedFileName)
                    viewModel.clearErrorMessage()
                }
            } else {
                viewModel.setErrorMessage("Selected file is not an image.")
            }
        } else {
            viewModel.setErrorMessage("No image selected.")
        }
    }

    Column(modifier = modifier.padding(8.dp)) {
        imageUri?.let { uri ->
            ImageUriCard(
                fileUri = uri,
                onDelete = {
                    viewModel.clearImage()
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
                onValueChange = { viewModel.onMessageChange(it) },
                modifier = Modifier.weight(1f),
                placeholder = "Masukkan Perintahmu",
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_add_image),
                        contentDescription = "Attach Image",
                        modifier = Modifier
                            .size(28.dp)
                            .clickable { launcher.launch("*/*") }
                    )
                }
            )
            IconButton(onClick = {
                if (isModelResponding) {
                    onCancelResponse()
                } else {
                    if (message.isNotEmpty() || imageUri != null) {
                        onMessageSend(message, imageUri?.toString())
                        viewModel.onMessageChange("")
                        viewModel.clearImage()
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

        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearErrorMessage()
        }
    }
}

