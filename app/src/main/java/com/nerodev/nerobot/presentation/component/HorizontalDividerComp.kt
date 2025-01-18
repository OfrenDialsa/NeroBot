package com.nerodev.nerobot.presentation.component

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.nerodev.nerobot.core.theme.NeroBotColor

@Composable
fun HorizontalDividerComp() {
    HorizontalDivider(
        color = NeroBotColor.Neutral100
    )
}

@Composable
fun ThiccHorizontalDividerComp() {
    HorizontalDivider(
        color = NeroBotColor.Neutral100,
        thickness = 2.dp
    )
}