package com.example.thescore.compose.reelscompose.presentation.model

data class ReelsUiModel(
    val title: String,
    val reels: List<ReelUiModel> = emptyList(),
)

data class ReelUiModel(
    val id: String,
    val videoUrl: String,
    val thumbnailUrl: String,
    val description: String,
    val likesCount: Int,
    val shareCount: Int,
    val isLiked: Boolean = false,
)
