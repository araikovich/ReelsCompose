package com.example.thescore.compose.reelscompose.presentation.composables

import android.view.ViewGroup
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.thescore.compose.reelscompose.presentation.model.ReelUiModel
import com.example.thescore.compose.reelscompose.presentation.util.VideoPlayerController

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun ReelVideo(
    item: ReelUiModel,
    pagerState: PagerState,
    index: Int,
) {
    val context = LocalContext.current
    val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)
    val thumbnailState = remember {
        mutableStateOf(true)
    }
    val videoController = remember {
        VideoPlayerController(context)
    }
    if (pagerState.currentPage == index) {
        DisposableEffect(key1 = context) {
            videoController.seekToStart()
            videoController.play()
            onDispose {
                videoController.pause()
            }
        }
    }
    LaunchedEffect(key1 = context) {
        videoController.initializePlayer(item.videoUrl) {
            thumbnailState.value = false
        }
        val lifeCycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP -> {
                    videoController.pause()
                }

                Lifecycle.Event.ON_START -> {
                    if (pagerState.currentPage == index) {
                        videoController.play()
                    }
                }

                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifeCycleObserver)
    }
    val playerView = remember {
        PlayerView(context).apply {
            player = videoController.player
            useController = false
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    if (thumbnailState.value) {
        AsyncImage(
            model = item.thumbnailUrl,
            contentDescription = "Thumbnail image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }

    AndroidView(
        factory = { playerView },
        modifier = Modifier.fillMaxSize().aspectRatio(9f / 16f)
    )

    DisposableEffect(key1 = playerView, effect = {
        onDispose {
            thumbnailState.value = true
            videoController.releasePlayer()
        }
    })
}