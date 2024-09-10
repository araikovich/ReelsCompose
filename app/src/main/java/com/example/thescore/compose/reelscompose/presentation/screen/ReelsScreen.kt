package com.example.thescore.compose.reelscompose.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.thescore.compose.reelscompose.R
import com.example.thescore.compose.reelscompose.presentation.composables.ReelsPager
import com.example.thescore.compose.reelscompose.presentation.viewmodel.ReelsViewModel

private const val ProgressBarWidth = 64
private const val TitleTextSize = 24
private const val TitleTextPadding = 24

@Composable
fun ReelsScreen(
    viewModel: ReelsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .safeDrawingPadding()
    ) {
        val data = state.value
        when {
            data.isError -> {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        text = stringResource(id = R.string.something_went_wrong),
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Button(
                        onClick = { viewModel.getReels() },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(stringResource(id = R.string.retry))
                    }
                }
            }

            data.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(ProgressBarWidth.dp)
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }

            else -> {
                ReelsPager(
                    items = data.reels?.reels.orEmpty(),
                    onLikeClick = { reelId ->
                        viewModel.onLike(reelId)
                    },
                    onShareClick = { reelId ->
                        viewModel.onShare(reelId)
                    })
                Text(
                    text = data.reels?.title.orEmpty(),
                    color = Color.White,
                    modifier = Modifier
                        .padding(TitleTextPadding.dp)
                        .align(Alignment.TopCenter),
                    style = TextStyle(fontSize = TitleTextSize.sp)
                )
            }
        }
    }
}