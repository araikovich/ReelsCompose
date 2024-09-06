package com.example.thescore.compose.reelscompose.data.api

import com.example.thescore.compose.reelscompose.data.model.ReelsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ReelsApi {

    @GET("clips/clipssample/clips")
    suspend fun getReels(): Response<ReelsResponse>
}