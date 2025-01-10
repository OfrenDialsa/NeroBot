package com.example.nerobot.presentation.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.nerobot.core.theme.NeroBotColor

@Composable
fun AIResponseText(
    aiResponse: String
) {
    Text(
        text = buildAnnotatedString {
            val boldRegex = Regex("\\*\\*(.*?)\\*\\*")
            val multiLineCodeRegex = Regex("```kotlin\\s*\\n([\\s\\S]*?)```")
            val plainCodeBlockRegex = Regex("```([\\s\\S]*?)```")
            val inlineCodeRegex = Regex("`(.*?)`")

            var lastIndex = 0

            val allMatches = sequence {
                yieldAll(boldRegex.findAll(aiResponse))
                yieldAll(multiLineCodeRegex.findAll(aiResponse))
                yieldAll(plainCodeBlockRegex.findAll(aiResponse))
                yieldAll(inlineCodeRegex.findAll(aiResponse))
            }.sortedBy { it.range.first }

            val processedRanges = mutableListOf<IntRange>()

            for (matchResult in allMatches) {

                if (processedRanges.any { it.intersect(matchResult.range).isNotEmpty() }) {
                    continue
                }


                if (lastIndex < matchResult.range.first) {
                    append(aiResponse.substring(lastIndex, matchResult.range.first))
                }

                val matchedText = matchResult.value
                when {
                    matchedText.startsWith("```") -> {
                        withStyle(SpanStyle(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 14.sp
                        )) {

                            val codeContent = matchResult.groupValues[1].trim()
                            append(codeContent)
                        }
                        processedRanges.add(matchResult.range)
                    }
                    matchedText.startsWith("`") -> {
                        withStyle(SpanStyle(
                            fontFamily = FontFamily.Monospace
                        )) {
                            append(matchResult.groupValues[1])
                        }
                        processedRanges.add(matchResult.range)
                    }
                    matchedText.startsWith("**") -> {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(matchResult.groupValues[1])
                        }
                    }
                }

                lastIndex = matchResult.range.last + 1
            }

            if (lastIndex < aiResponse.length) {
                append(aiResponse.substring(lastIndex))
            }
        },
        fontSize = 16.sp
    )
}