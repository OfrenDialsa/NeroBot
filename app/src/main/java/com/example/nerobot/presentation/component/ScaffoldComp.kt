package com.example.nerobot.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.unit.dp

@Composable
fun ScaffoldComp(
    topBar: (@Composable () -> Unit)? = null,
    content: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    modifier: Modifier
) {
    Scaffold(
        topBar = {
            topBar?.invoke()
        },
        content = { padding ->
            Column(
                modifier
                    .padding(0.dp, top = 62.dp)
            ) {
                content?.invoke()
            }
        },
        bottomBar = {
            bottomBar?.invoke()
        }
    )
}