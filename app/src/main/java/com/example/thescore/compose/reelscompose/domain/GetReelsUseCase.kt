package com.example.thescore.compose.reelscompose.domain

import com.example.thescore.compose.reelscompose.domain.repository.ReelsRepository
import com.example.thescore.compose.reelscompose.data.util.map
import com.example.thescore.compose.reelscompose.di.Dispatcher
import com.example.thescore.compose.reelscompose.di.ReelsDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetReelsUseCase @Inject constructor(
    private val reelsRepository: ReelsRepository,
    @Dispatcher(ReelsDispatchers.IO) private val coroutineDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke() = withContext(coroutineDispatcher) {
        reelsRepository.getReels().map { reelResult ->
            ReelsModel(
                feedTitle = reelResult.feedTitle,
                reels = reelResult.reels?.map { reel ->
                    ReelModel(
                        id = reel.id,
                        description = reel.description,
                        thumbnailUrl = reel.thumbnailUrl,
                        videoUrl = reel.videoUrl,
                        likesCount = reel.likesCount,
                        shareCount = reel.shareCount,
                    )
                }?.filterNot { it.id.isNullOrEmpty() }
            )
        }
    }
}