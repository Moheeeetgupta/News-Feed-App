package com.moengage.testapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moengage.testapp.R
import com.moengage.testapp.entities.Article
import com.moengage.testapp.ui.theme.backgroundSecondary
import com.moengage.testapp.ui.theme.borderPrimary
import com.moengage.testapp.ui.theme.textPrimary
import com.moengage.testapp.ui.theme.textQuaternary
import com.moengage.testapp.ui.theme.textSecondary
import com.moengage.testapp.ui.theme.textTertiary


@Composable
fun NewsFeedDetailCard(newsFeedItem: Article) {
    val uriHandler = LocalUriHandler.current
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .background(backgroundSecondary, RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = borderPrimary, RoundedCornerShape(12.dp))
            .clickable {
                uriHandler.openUri(newsFeedItem.url)
            }
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            ShowImage(newsFeedItem.urlToImage)
            Spacer(Modifier.width(5.dp))
            Text(
                text = newsFeedItem.title,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = textPrimary,
                    fontWeight = FontWeight.Bold
                ),
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                letterSpacing = 0.sp,
                maxLines = 1,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                newsFeedItem.author?.let {
                    Text(
                        text = it,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        color = textSecondary,
                        letterSpacing = 0.sp,
                        maxLines = 1
                    )
                    Text(
                        text = stringResource(R.string.dot),
                        color = textSecondary,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
                Text(
                    text = stringResource(R.string.at_the_rate) + newsFeedItem.source.name,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    color = textSecondary,
                    letterSpacing = 0.sp,
                    maxLines = 1
                )
            }
            newsFeedItem.content?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    color = textTertiary,
                    letterSpacing = 0.sp
                )
            }
            Text(
                text = newsFeedItem.publishedAt,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                ),
                color = textQuaternary,
                letterSpacing = 0.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
