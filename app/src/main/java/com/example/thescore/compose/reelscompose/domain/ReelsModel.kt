package com.example.thescore.compose.reelscompose.domain

data class ReelsModel(
    val feedTitle: String?,
    val reels: List<ReelModel>?,
)

data class ReelModel(
    val id: String?,
    val description: String?,
    val thumbnailUrl: String?,
    val videoUrl: String?,
    val likesCount: Int?,
    val shareCount: Int?,
)