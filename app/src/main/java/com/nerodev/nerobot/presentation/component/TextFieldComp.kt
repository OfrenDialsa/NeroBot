package com.nerodev.nerobot.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.selection.TextSelectionColors
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nerodev.nerobot.core.theme.NeroBotColor

@Composable
fun TextFieldComp(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    onClick: (() -> Unit)? = null,
    leadingIcon: (@Composable (() -> Unit))? = null,
    leadingIconOnClick: (() -> Unit)? = null,
    trailingIcon: (@Composable (() -> Unit))? = null,
    trailingIconOnClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = FocusRequester()
    val isDarkTheme = isSystemInDarkTheme()

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = NeroBotColor.Neutral300) },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 36.dp)
            .focusRequester(focusRequester)
            .onFocusChanged { focusState -> isFocused = focusState.isFocused }
            .clickable(onClick = { onClick?.invoke() }),
        shape = MaterialTheme.shapes.extraLarge,
        leadingIcon = {
            leadingIcon?.let {
                Modifier
                    .clip(shape = MaterialTheme.shapes.extraLarge)
                    .clickable {
                    leadingIconOnClick?.invoke()
                }.run {
                    it()
                }
            }
        },
        trailingIcon = {
            trailingIcon?.let {
                Modifier.clickable {
                    trailingIconOnClick?.invoke()
                }.run {
                    it()
                }
            }
        },
        maxLines = 5,
        colors = TextFieldDefaults.colors(
            cursorColor = NeroBotColor.Green300,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            selectionColors = TextSelectionColors(
                handleColor = NeroBotColor.Green300,
                backgroundColor = if (isDarkTheme) NeroBotColor.Green500 else NeroBotColor.Green100
            )
        ),
        textStyle = LocalTextStyle.current.copy(
            lineHeight = 18.sp
        )
    )
}