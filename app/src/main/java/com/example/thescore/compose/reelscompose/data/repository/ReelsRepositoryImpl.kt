package com.example.thescore.compose.reelscompose.data.repository

import com.example.thescore.compose.reelscompose.data.datasource.ReelsNetworkDataSource
import com.example.thescore.compose.reelscompose.data.util.ResultWrapper
import com.example.thescore.compose.reelscompose.data.util.map
import com.example.thescore.compose.reelscompose.domain.ReelModel
import com.example.thescore.compose.reelscompose.domain.ReelsModel
import com.example.thescore.compose.reelscompose.domain.repository.ReelsRepository
import javax.inject.Inject

class ReelsRepositoryImpl @Inject constructor(
    private val reelsNetworkDataStorage: ReelsNetworkDataSource
) : ReelsRepository {

    override suspend fun getReels(): ResultWrapper<ReelsModel> {
        return reelsNetworkDataStorage.getReels().map {
            ReelsModel(
                feedTitle = it.feedTitle,
                reels = it.reels?.mapNotNull { reel ->
                    ReelModel(
                        id = reel?.id,
                        description = reel?.description,
                        thumbnailUrl = reel?.thumbnailUrl,
                        videoUrl = reel?.url,
                        likesCount = reel?.likeCountDisplay,
                        shareCount = reel?.shareCountDisplay,
                    )
                }.orEmpty()
            )
        }
    }
}