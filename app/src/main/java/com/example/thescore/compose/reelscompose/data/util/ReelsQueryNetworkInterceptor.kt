package com.example.thescore.compose.reelscompose.data.util

import com.example.thescore.compose.reelscompose.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ReelsQueryNetworkInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter(API_KEY_QUERY_PARAM, BuildConfig.API_KEY)
            .addQueryParameter(PLATFORM_QUERY_PARAM, PLATFORM_VALUE)
            .addQueryParameter(CLIENT_VERSION_QUERY_PARAM, BuildConfig.VERSION_NAME)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }

    companion object {

        private const val API_KEY_QUERY_PARAM = "x-storyteller-api-key"
        private const val PLATFORM_QUERY_PARAM = "ClientPlatform"
        private const val PLATFORM_VALUE = "Android"
        private const val CLIENT_VERSION_QUERY_PARAM = "ClientVersion"
    }
}