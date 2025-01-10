package com.example.nerobot.presentation.screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import com.example.nerobot.R
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nerobot.core.theme.NeroBotColor

@Composable
fun DrawerContent(
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isDarkTheme = isSystemInDarkTheme()
    val selectedTextColor = if (isDarkTheme) NeroBotColor.Black else NeroBotColor.White
    val selectedContainerColor = if (isDarkTheme) NeroBotColor.Neutral500DarkMode else NeroBotColor.Neutral300
    val selectedIconColor = if (isDarkTheme) NeroBotColor.ForestGreen else NeroBotColor.KellyGreen

    Spacer(modifier.height(16.dp))

    Icon(
        modifier = Modifier
            .padding(start = 12.dp)
            .size(56.dp),
        painter = painterResource(id = R.drawable.ic_logo),
        contentDescription = "Icon",
        tint = NeroBotColor.ForestGreen
    )
    Text(
        text = buildAnnotatedString {
            append("NeroBot")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = NeroBotColor.KellyGreen)) {
                append("  ")
            }
            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, fontSize = 12.sp)) {
                append("AI ChatBot")
            }
        },
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        modifier = Modifier.padding(16.dp),
    )

    HorizontalDivider()

    Spacer(modifier.height(16.dp))

    Box(modifier = Modifier.fillMaxSize()){
        Column {
            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Chat",
                        fontSize = 16.sp,
                    )
                },
                selected = selectedItem == "chat",
                onClick = { onItemSelected("chat") }, // Unique route for this item
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_chat),
                        contentDescription = "Chat",
                        tint = selectedIconColor,
                        modifier = modifier.size(26.dp)
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = selectedContainerColor,
                    selectedTextColor = selectedTextColor
                ),
                modifier = Modifier
                    .sizeIn(minHeight = 38.dp, maxHeight = 46.dp)
                    .padding(horizontal = 6.dp)
            )

            Spacer(modifier.height(8.dp))

            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Saved",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                },
                selected = selectedItem == "saved",
                onClick = { onItemSelected("saved") }, // Unique route for this item
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_saved),
                        contentDescription = "Saved",
                        tint = selectedIconColor,
                        modifier = modifier
                            .size(20.dp)
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = selectedContainerColor,
                    selectedIconColor = selectedTextColor,
                    selectedTextColor = selectedTextColor
                ),
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .sizeIn(minHeight = 38.dp, maxHeight = 46.dp)
            )

            Spacer(modifier.height(8.dp))

            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Latest News",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                },
                selected = selectedItem == "news",
                onClick = { onItemSelected("news") },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_news),
                        contentDescription = "News",
                        tint = selectedIconColor,
                        modifier = modifier.size(22.dp)
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = selectedContainerColor,
                    selectedIconColor = selectedTextColor,
                    selectedTextColor = selectedTextColor
                ),
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .sizeIn(minHeight = 38.dp, maxHeight = 46.dp)
            )
        }

        Text(
            "Alpha version",
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(bottom = 18.dp)
        )
    }

}