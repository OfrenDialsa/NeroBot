package com.example.nerobot.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import com.example.nerobot.R
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.nerobot.core.theme.NeroBotColor
import java.text.DecimalFormat

@Composable
fun TextFieldComp(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false,
    isPhone: Boolean = false,
    isValid: Boolean = false,
    isError: Boolean = false,
    isNominal: Boolean = false,
    isDropdown: Boolean = false,
    readOnly: Boolean = false,
    errorMessage: String = "",
    validMessage: String = "",
    onClick: (() -> Unit)? = null,
    leadingIcon: (@Composable (() -> Unit))? = null,
    modifier: Modifier = Modifier
){
    var isFocused by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(!isPassword) }
    val focusRequester = FocusRequester()

    fun formatAsIDRCurrency(input: String): String {
        val cleanInput = input.replace(Regex("[^0-9]"), "")
        return if (cleanInput.isNotEmpty()) {
            val number = cleanInput.toLong()
            val formatter = DecimalFormat("#,###")
            formatter.format(number)
        } else {
            ""
        }
    }

    fun formatValue(input: String): String {
        return if (isNominal) formatAsIDRCurrency(input) else input
    }

    val displayValue = formatValue(value)

    OutlinedTextField(
        value = displayValue,
        onValueChange = { input ->
            val formattedValue = formatValue(input)
            onValueChange(formattedValue)
        },
        placeholder = { Text(placeholder, color = Color.Gray) },
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged { focusState -> isFocused = focusState.isFocused }
            .border(
                width = if (isFocused) 1.8.dp else 1.25.dp,
                color = when {
                    isError -> NeroBotColor.Red500
                    isValid -> NeroBotColor.Green500
                    isFocused -> NeroBotColor.ForestGreen
                    else -> NeroBotColor.Neutral50
                },
                shape = MaterialTheme.shapes.medium
            )
            .clickable(enabled = isDropdown || readOnly, onClick = { onClick?.invoke() }),
        singleLine = false, // Changed to false to allow multiple lines
        minLines = 1, // Minimum height will be 1 line
        maxLines = 5, // Maximum of 5 lines before scrolling
        readOnly = readOnly,
        shape = MaterialTheme.shapes.medium,
        keyboardOptions = KeyboardOptions(
            keyboardType = when {
                isDropdown -> KeyboardType.Text
                isPassword -> if(!isVisible) KeyboardType.Password else KeyboardType.Text
                isPhone -> KeyboardType.Phone
                isNominal -> KeyboardType.Number
                else -> KeyboardType.Unspecified
            }
        ),
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(
            mask = '●'
        ),
        leadingIcon = leadingIcon,
        trailingIcon = {
            when {
                isDropdown -> {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = NeroBotColor.Black
                    )
                }
                isPassword -> {
                    IconButton(onClick = { isVisible = !isVisible }) {
                        Icon(
                            painter = painterResource(id = if (isVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility_ol),
                            contentDescription = null,
                            tint = if (isVisible) NeroBotColor.Blue500 else Color.Gray
                        )
                    }
                }
            }
        },
        isError = isError
    )

    if (isError || isValid) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = if (isError) R.drawable.ic_error else R.drawable.ic_success),
                contentDescription = null,
                tint = if (isError) NeroBotColor.Red500 else NeroBotColor.Green500,
                modifier = Modifier
                    .size(24.dp)
                    .padding(top = 4.dp)
            )
            Text(
                text = if (isError) errorMessage else validMessage,
                color = if (isError) NeroBotColor.Red500 else NeroBotColor.Green500,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
    }
}