package com.nerodev.nerobot.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nerodev.nerobot.R
import com.nerodev.nerobot.core.theme.NeroBotColor
import com.nerodev.nerobot.domain.model.MessageDomainModel

@Composable
fun MessageRole(messageDomainModel: MessageDomainModel) {
    val isModel = messageDomainModel.role == "model"
    val clipboardManager = LocalClipboardManager.current
    val isDarkTheme = isSystemInDarkTheme()

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .align(if (isModel) Alignment.BottomStart else Alignment.BottomEnd)
                    .padding(
                        start = if (isModel) 8.dp else 70.dp,
                        end = if (isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    ),
                horizontalAlignment = if (isModel) Alignment.Start else Alignment.End
            ) {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isModel) {
                            Color.Unspecified
                        } else {
                            if (isDarkTheme) NeroBotColor.Green600 else NeroBotColor.Green200
                        }
                    ),
                    modifier = Modifier
                        .clickable {
                            clipboardManager.setText(AnnotatedString(messageDomainModel.message))
                        }
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        AIResponseText(
                            aiResponse = messageDomainModel.message
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MessageList(
    modifier: Modifier = Modifier,
    messageList: List<MessageDomainModel>,
    navController: NavController
) {
    val greetingOptions = listOf(
        "NeroBot siap membantu",
        "Wazzup?",
        "Ada pertanyaan?",
        "Sokin ngab",
        "Halo Manusia"
    )

    if (messageList.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                modifier = Modifier.size(80.dp),
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Icon",
                tint = NeroBotColor.ForestGreen
            )
            Spacer(modifier = Modifier.height(8.dp))
            val randomGreeting = greetingOptions.random()
            TypingTextAnimation(
                text = randomGreeting,
                navController = navController
            )
        }

    } else {
        LazyColumn(
            modifier = modifier,
            reverseLayout = true
        ) {
            items(messageList.reversed()) {
                MessageRole(messageDomainModel = it)
            }
        }
    }
}