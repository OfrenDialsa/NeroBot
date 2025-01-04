package com.example.nerobot.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nerobot.core.theme.NeroBotColor

@Composable
fun TitleTextComp() {
    Row {
        Text(
            text = "NeroBot",
            color = NeroBotColor.KellyGreen,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp // Set the font size for the main text
        )
        Spacer(modifier = Modifier.width(4.dp)) // Add spacing between the texts
        Text(
            text = "- Powered By Gemini API",
            color = NeroBotColor.KellyGreen,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp // Set a smaller font size for the secondary text
        )
    }
}