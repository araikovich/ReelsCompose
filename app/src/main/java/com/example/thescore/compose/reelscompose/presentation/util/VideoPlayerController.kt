package com.example.thescore.compose.reelscompose.presentation.util

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer

@UnstableApi
class VideoPlayerController(context: Context) {

    private val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()

    val player: ExoPlayer get() = exoPlayer

    fun initializePlayer(videoUrl: String, onVideoStart: () -> Unit) {
        exoPlayer.apply {
            val mediaItem = MediaItem.fromUri(videoUrl)
            setMediaItem(mediaItem)
            repeatMode = Player.REPEAT_MODE_ONE
            playWhenReady = false
            addListener(object : Player.Listener {
                override fun onRenderedFirstFrame() {
                    super.onRenderedFirstFrame()
                    onVideoStart()
                }
            })
            exoPlayer.prepare()
        }
    }

    fun play() {
        exoPlayer.play()
    }

    fun pause() {
        exoPlayer.pause()
    }

    fun seekToStart() {
        exoPlayer.seekTo(0)
    }

    fun releasePlayer() {
        exoPlayer.release()
    }
}