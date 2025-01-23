package com.nerodev.nerobot.presentation.screen.newspage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nerodev.nerobot.core.utils.Result
import com.nerodev.nerobot.presentation.component.NewsCard
import com.nerodev.nerobot.presentation.viewmodel.NewsViewModelImpl
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsPage(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: NewsViewModelImpl = koinViewModel()

    LaunchedEffect(Unit) {
        viewModel.getAllNews()
    }

    val result = viewModel.news.collectAsState().value

    when (result) {
        is Result.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Result.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val newsItems = result.data.articles
                items(newsItems) { newsItem ->
                    NewsCard(
                        title = newsItem.title,
                        description = newsItem.description,
                        imageUrl = newsItem.urlToImage ?: "",
                        url = newsItem.url
                    )
                }
            }
        }

        is Result.Error -> {
            Text(
                text = "Error: ${result.exception}",
                modifier = modifier.padding(16.dp)
            )
        }
    }
}
