package com.moengage.testapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.moengage.testapp.ui.composables.MainScreenComposable
import com.moengage.testapp.ui.theme.MoEngageTestAppTheme
import com.moengage.testapp.viewmodels.NewsFeedViewModel

class MainActivity : ComponentActivity() {
    private val newsFeedViewModel by viewModels<NewsFeedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoEngageTestAppTheme {
                MainScreenComposable(newsFeedViewModel = newsFeedViewModel)
            }
        }
    }
}

