package com.moengage.testapp.networking

import com.moengage.testapp.entities.NewsFeedResponse
import com.moengage.testapp.networking.annotations.GET

interface ApiServiceInterface {

    @GET("/Android/news-api-feed/staticResponse.json")
    suspend fun getNewsFeed(): NewsFeedResponse?

}