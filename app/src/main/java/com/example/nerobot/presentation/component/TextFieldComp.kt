package com.example.nerobot.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.nerobot.core.theme.NeroBotColor

@Composable
fun TextFieldComp(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    onClick: (() -> Unit)? = null,
    leadingIcon: (@Composable (() -> Unit))? = null,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = FocusRequester()

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = NeroBotColor.Neutral300) },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp, max = 100.dp)
            .focusRequester(focusRequester)
            .onFocusChanged { focusState -> isFocused = focusState.isFocused }
            .clickable(onClick = { onClick?.invoke() }),
        shape = MaterialTheme.shapes.extraLarge,
        leadingIcon = leadingIcon,
        maxLines = 5,
        colors = TextFieldDefaults.colors(
            cursorColor = if (isFocused) NeroBotColor.Green300 else LocalTextStyle.current.color,
            focusedIndicatorColor = NeroBotColor.Green400,
            unfocusedIndicatorColor = NeroBotColor.Green500
        )
    )
}