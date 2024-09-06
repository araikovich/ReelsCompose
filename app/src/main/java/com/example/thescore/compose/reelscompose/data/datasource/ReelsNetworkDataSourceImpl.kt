package com.example.thescore.compose.reelscompose.data.datasource

import com.example.thescore.compose.reelscompose.data.api.ReelsApi
import com.example.thescore.compose.reelscompose.data.model.ReelsResponse
import com.example.thescore.compose.reelscompose.data.util.ResultWrapper
import com.example.thescore.compose.reelscompose.data.util.safeApiCall
import javax.inject.Inject

class ReelsNetworkDataSourceImpl @Inject constructor(
    private val reelsService: ReelsApi
) : ReelsNetworkDataSource {

    override suspend fun getReels(): ResultWrapper<ReelsResponse> {
        return safeApiCall {
            reelsService.getReels()
        }
    }
}