package com.moengage.testapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.moengage.testapp.R
import com.moengage.testapp.entities.Response
import com.moengage.testapp.entities.SortingTypes
import com.moengage.testapp.ui.theme.backgroundPrimary
import com.moengage.testapp.ui.theme.backgroundSecondary
import com.moengage.testapp.viewmodels.NewsFeedViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenComposable(newsFeedViewModel: NewsFeedViewModel) {
    LaunchedEffect(key1 = Unit) {
        newsFeedViewModel.getNewsFeed()
    }
    val context = LocalContext.current
    val (sortingType, setSortingType) = remember {
        mutableStateOf(SortingTypes.Default)
    }
    Scaffold(
        modifier = Modifier
            .background(backgroundPrimary)
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.news_app), color = backgroundSecondary)
                },
                actions = {
                    OverflowMenu {
                        DropdownMenuItem(
                            modifier = Modifier.background(
                                if (sortingType == SortingTypes.Default) {
                                    backgroundPrimary
                                } else {
                                    backgroundSecondary
                                }
                            ),
                            onClick = { setSortingType(SortingTypes.Default) }, text = {
                                Text(text = stringResource(R.string.default_sorting_type))
                            }
                        )
                        DropdownMenuItem(
                            modifier = Modifier.background(
                                if (sortingType == SortingTypes.NewToOld) {
                                    backgroundPrimary
                                } else {
                                    backgroundSecondary
                                }
                            ),
                            onClick = { setSortingType(SortingTypes.NewToOld) },
                            text = {
                                Text(text = stringResource(R.string.new_to_old))
                            }
                        )
                        DropdownMenuItem(
                            modifier = Modifier.background(
                                if (sortingType == SortingTypes.OldToNew) {
                                    backgroundPrimary
                                } else {
                                    backgroundSecondary
                                }
                            ),
                            onClick = { setSortingType(SortingTypes.OldToNew) },
                            text = {
                                Text(text = stringResource(R.string.old_to_new))
                            })
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),

                )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (newsFeedViewModel.isNetworkAvailable(context = context)) {
                when (val newsFeedState = newsFeedViewModel.newsFeedState.value) {
                    is Response.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(it)
                                .wrapContentSize()
                                .align(Alignment.Center)
                        )
                    }

                    is Response.Success -> {
                        val dateFormat =
                            SimpleDateFormat(
                                stringResource(R.string.api_date_format),
                                Locale.ENGLISH
                            )

                        NewsFeedComposable(
                            articles = when (sortingType) {
                                SortingTypes.Default -> newsFeedState.data?.articles
                                SortingTypes.OldToNew -> newsFeedState.data?.articles?.sortedBy { article ->
                                    dateFormat.parse(
                                        article.publishedAt
                                    )
                                }

                                SortingTypes.NewToOld -> newsFeedState.data?.articles?.sortedByDescending { article ->
                                    dateFormat.parse(
                                        article.publishedAt
                                    )
                                }
                            },
                            modifier = Modifier
                                .padding(it)
                                .wrapContentSize()
                                .align(Alignment.Center)
                        ) { publishedAt ->
                            newsFeedViewModel.formatDateTime(publishedAt)
                        }
                    }

                    is Response.Failure -> {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                newsFeedViewModel.getNewsFeed()
                            }
                        ) {
                            Text(text = stringResource(R.string.error_occurred_please_try_again))
                        }
                    }
                }
            } else {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.please_check_your_internet_connection)
                )

            }
        }
    }
}

