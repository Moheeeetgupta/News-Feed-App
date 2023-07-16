package com.moengage.testapp.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moengage.testapp.entities.Article

@Composable
fun NewsFeedComposable(
    modifier: Modifier = Modifier,
    articles: List<Article>?, formatDate: (String) -> String
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp)) {
            articles?.let {
                items(it) { article ->
                    NewsFeedDetailCard(article.copy(publishedAt = formatDate(article.publishedAt)))
                }
            }

        }
    }
}