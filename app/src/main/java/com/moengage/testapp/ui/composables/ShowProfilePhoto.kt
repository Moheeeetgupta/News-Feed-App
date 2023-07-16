package com.moengage.testapp.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun ShowImage(
    imageUrl: String
) {
    val modifier = Modifier.fillMaxWidth()
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = modifier,
    )

}