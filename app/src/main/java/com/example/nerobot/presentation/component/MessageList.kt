package com.example.nerobot.presentation.component

import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nerobot.domain.model.MessageDomainModel
import com.example.nerobot.R
import com.example.nerobot.core.theme.NeroBotColor

@Composable
fun MessageRole(messageDomainModel: MessageDomainModel) {
    val isModel = messageDomainModel.role == "model"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .align(if (isModel) Alignment.BottomStart else Alignment.BottomEnd)
                    .padding(
                        start = if (isModel) 8.dp else 70.dp,
                        end = if (isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(if (isModel) NeroBotColor.ForestGreen else NeroBotColor.KellyGreen)
                    .padding(16.dp)
            ) {
                Text(
                    text = messageDomainModel.message,
                    fontWeight = FontWeight.W500,
                    color = NeroBotColor.White
                )

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
    if (messageList.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(80.dp),
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Icon",
                tint = NeroBotColor.ForestGreen
            )
            Spacer(modifier = Modifier.height(8.dp))
            TypingTextAnimation(text = "Selamat Datang, Atmin", navController = navController)
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