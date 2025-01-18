package com.nerodev.nerobot.core.utils

fun removeAsterisks(text: String): String {
    return text.replace(Regex("\\*{1,2}(.*?)\\*{1,2}")) { matchResult ->
        matchResult.groupValues[1]
    }
}