package com.example.thescore.compose.reelscompose.data.model

import com.google.gson.annotations.SerializedName

data class ReelsResponse(
    @SerializedName("feedTitle") val feedTitle: String?,
    @SerializedName("clips") val reels: List<ReelResponse?>?,
)

data class ReelResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("thumbnailUrl") val thumbnailUrl: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("likeCountDisplay") val likeCountDisplay: Int?,
    @SerializedName("shareCountDisplay") val shareCountDisplay: Int?,
)