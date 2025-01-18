package com.nerodev.nerobot.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nerodev.nerobot.core.theme.NeroBotColor

@Composable
fun ImageUriCard(fileName: String, onDelete: () -> Unit) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(bottom = 4.dp)// Reduced vertical padding for a smaller height
            .fillMaxWidth(),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(2.dp) // Reduced elevation for a thinner look
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp) // Compact internal padding
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = fileName,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp), // Space between text and button
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(24.dp) // Smaller button size
            ) {
                Icon(
                    imageVector = Icons.Default.Close, // Default delete icon
                    contentDescription = "Delete Image",
                    tint = NeroBotColor.Neutral300
                )
            }
        }
    }
}