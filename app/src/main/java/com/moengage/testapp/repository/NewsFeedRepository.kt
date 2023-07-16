package com.moengage.testapp.repository

import com.moengage.testapp.entities.NewsFeedResponse
import com.moengage.testapp.entities.Response
import kotlinx.coroutines.flow.Flow

interface NewsFeedRepository {
    fun getNewsFeed(): Flow<Response<NewsFeedResponse>>
}