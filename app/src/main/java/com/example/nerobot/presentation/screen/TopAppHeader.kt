package com.example.nerobot.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nerobot.core.theme.NeroBotColor
import com.example.nerobot.presentation.component.HorizontalDividerComp
import com.example.nerobot.presentation.component.ThiccHorizontalDividerComp

@Composable
fun AppHeader() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "NeroBot",
                color = NeroBotColor.KellyGreen,
                fontSize = 22.sp,
            )
        }
    }
    HorizontalDividerComp()
}