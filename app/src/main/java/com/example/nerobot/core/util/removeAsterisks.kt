package com.example.nerobot.core.util

fun removeAsterisks(text: String): String {
    return text.replace(Regex("\\*{1,2}(.*?)\\*{1,2}")) { matchResult ->
        matchResult.groupValues[1]
    }
}