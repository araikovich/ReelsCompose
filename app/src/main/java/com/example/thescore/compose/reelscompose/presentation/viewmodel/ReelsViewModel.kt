package com.example.thescore.compose.reelscompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thescore.compose.reelscompose.domain.GetReelsUseCase
import com.example.thescore.compose.reelscompose.presentation.model.ReelUiModel
import com.example.thescore.compose.reelscompose.presentation.model.ReelsUiModel
import com.example.thescore.compose.reelscompose.presentation.util.orZero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReelsViewModel @Inject constructor(
    private val getReelsUseCase: GetReelsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ReelsState())
    val state: StateFlow<ReelsState>
        get() = _state

    init {
        getReels()
    }

    fun getReels() {
        _state.value = _state.value.copy(isLoading = true, isError = false)
        viewModelScope.launch {
            getReelsUseCase().doOnResult(
                onSuccess = { reelsModel ->
                    _state.value = _state.value.copy(
                        reels = ReelsUiModel(
                            title = reelsModel.feedTitle.orEmpty(),
                            reels = reelsModel.reels?.map { reelModel ->
                                ReelUiModel(
                                    id = reelModel.id.orEmpty(),
                                    description = reelModel.description.orEmpty(),
                                    thumbnailUrl = reelModel.thumbnailUrl.orEmpty(),
                                    videoUrl = reelModel.videoUrl.orEmpty(),
                                    likesCount = reelModel.likesCount.orZero(),
                                    shareCount = reelModel.shareCount.orZero(),
                                )
                            }.orEmpty()

                        ),
                        isLoading = false,
                        isError = false,
                    )
                },
                onError = {
                    _state.value = _state.value.copy(isError = true, isLoading = false)
                }
            )
        }
    }

    // This function is used to simulate the local actions like like/unlike
    // In real world, this should be stored on server or at least in local database
    fun onLike(reelId: String) {
        val currentReels = _state.value.reels?.reels.orEmpty()
        val updatedReels = currentReels.map { reel ->
            if (reel.id == reelId) {
                reel.copy(
                    isLiked = !reel.isLiked,
                    likesCount = if (reel.isLiked) reel.likesCount - 1 else reel.likesCount + 1
                )
            } else {
                reel
            }
        }
        _state.value = _state.value.copy(
            reels = _state.value.reels?.copy(reels = updatedReels)
        )
    }

    // This function is used to simulate the local actions like share
    // In real world, this should be stored on server or at least in local database
    fun onShare(reelId: String) {
        val currentReels = _state.value.reels?.reels.orEmpty()
        val updatedReels = currentReels.map { reel ->
            if (reel.id == reelId) {
                reel.copy(
                    shareCount = reel.shareCount + 1
                )
            } else {
                reel
            }
        }
        _state.value = _state.value.copy(
            reels = _state.value.reels?.copy(reels = updatedReels)
        )
    }
}

data class ReelsState(
    val reels: ReelsUiModel? = null,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
)