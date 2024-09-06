package com.example.thescore.compose.reelscompose.domain.repository

import com.example.thescore.compose.reelscompose.data.util.ResultWrapper
import com.example.thescore.compose.reelscompose.domain.ReelsModel

interface ReelsRepository {

    suspend fun getReels(): ResultWrapper<ReelsModel>
}