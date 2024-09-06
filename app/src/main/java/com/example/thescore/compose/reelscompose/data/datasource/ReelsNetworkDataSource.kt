package com.example.thescore.compose.reelscompose.data.datasource

import com.example.thescore.compose.reelscompose.data.model.ReelsResponse
import com.example.thescore.compose.reelscompose.data.util.ResultWrapper

interface ReelsNetworkDataSource {

    suspend fun getReels(): ResultWrapper<ReelsResponse>
}