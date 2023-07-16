package com.moengage.testapp.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moengage.testapp.entities.Constants.DATE_FORMAT_FOR_UI
import com.moengage.testapp.entities.Constants.DATE_FORMAT_FROM_SERVER
import com.moengage.testapp.entities.Constants.EMPTY
import com.moengage.testapp.entities.NewsFeedResponse
import com.moengage.testapp.entities.Response
import com.moengage.testapp.repository.NewsFeedRepository
import com.moengage.testapp.repository.NewsFeedRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class NewsFeedViewModel(private val newsFeedRepository: NewsFeedRepository = NewsFeedRepositoryImpl()) :
    ViewModel() {
    private val _newsFeedState = mutableStateOf<Response<NewsFeedResponse>>(Response.Success(null))
    val newsFeedState: State<Response<NewsFeedResponse>> = _newsFeedState

    fun getNewsFeed() {
        viewModelScope.launch(Dispatchers.IO) {
            newsFeedRepository.getNewsFeed().collect { response ->
                _newsFeedState.value = response
            }
        }
    }

    fun formatDateTime(dateFromServer: String?): String {
        try {
            val inputFormat = SimpleDateFormat(DATE_FORMAT_FROM_SERVER, Locale.ENGLISH)
            val outputFormat = SimpleDateFormat(DATE_FORMAT_FOR_UI, Locale.ENGLISH).apply {
                timeZone = TimeZone.getDefault()
            }
            return dateFromServer?.let {
                inputFormat.parse(it)?.let { inputDate -> outputFormat.format(inputDate) }
            } ?: EMPTY
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return EMPTY
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    ?: return false
            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } else {
            if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
                return true
            }
        }
        return false
    }
}