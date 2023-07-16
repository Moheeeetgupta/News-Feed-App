package com.moengage.testapp.repository

import com.moengage.testapp.networking.ApiServiceImpl
import com.moengage.testapp.networking.ApiServiceInterface
import com.moengage.testapp.entities.NewsFeedResponse
import com.moengage.testapp.entities.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsFeedRepositoryImpl constructor(private val apiService: ApiServiceInterface = ApiServiceImpl()) :
    NewsFeedRepository {

    override fun getNewsFeed(): Flow<Response<NewsFeedResponse>> = flow {
        try {
            emit(Response.Loading)
            val responseApi = apiService.getNewsFeed()
            emit(Response.Success(responseApi))
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}