package com.moengage.testapp.networking

import android.util.Log
import com.google.gson.Gson
import com.moengage.testapp.networking.annotations.AnnotationsUtility
import com.moengage.testapp.entities.NewsFeedResponse
import com.moengage.testapp.networking.annotations.GET
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

private const val BASE_URL = "https://candidate-test-data-moengage.s3.amazonaws.com"

class ApiServiceImpl : ApiServiceInterface {

    @GET("/Android/news-api-feed/staticResponse.json")
    override suspend fun getNewsFeed(): NewsFeedResponse? {
        return AnnotationsUtility.getAnnotationData(object {}.javaClass.enclosingMethod)
            ?.let { makeApiCall(it.endPoint, it.requestType) }
    }

    private suspend fun makeApiCall(endPoint: String, requestType: String): NewsFeedResponse? {
        var httpURLConnection: HttpURLConnection? = null
        var responseCode: Int?
        var apiResponse: NewsFeedResponse? = null
        try {
            httpURLConnection = (withContext(Dispatchers.IO) {
                URL(BASE_URL.plus(endPoint)).openConnection()
            } as HttpURLConnection).apply {
                connectTimeout = 10000
                readTimeout = 10000
                requestMethod = requestType
                responseCode = this.responseCode
            }
            if (responseCode != 200) {
                throw IOException("The error from the server is $responseCode")
            }
            val reader = InputStreamReader(httpURLConnection.inputStream)

            reader.use { input ->
                val response = StringBuilder()
                BufferedReader(input).forEachLine {
                    response.append(it.trim())
                }
                apiResponse = Gson().fromJson(response.toString(), NewsFeedResponse::class.java)

            }

        } catch (ioexception: IOException) {
            throw IOException(ioexception.message)
        } finally {
            httpURLConnection?.disconnect()
        }
        return apiResponse
    }
}