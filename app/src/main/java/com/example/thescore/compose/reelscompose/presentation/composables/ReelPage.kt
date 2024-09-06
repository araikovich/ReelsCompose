package com.example.thescore.compose.reelscompose.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.thescore.compose.reelscompose.presentation.model.ReelUiModel

private const val DescriptionPaddingStart = 16
private const val DescriptionPaddingBottom = 80
private const val ActionContentPaddingBottom = 80
private const val ActionContentPaddingEnd = 20

@Composable
fun ReelPage(
    item: ReelUiModel,
    pagerState: PagerState,
    index: Int,
    onLikeClick: (String) -> Unit,
    onShareClick: (String)  -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        ReelVideo(
            item = item,
            pagerState = pagerState,
            index = index,
        )
        Text(
            text = item.description,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = DescriptionPaddingStart.dp,
                    bottom = DescriptionPaddingBottom.dp
                )
        )
        ReelActions(
            item = item,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    bottom = ActionContentPaddingBottom.dp,
                    end = ActionContentPaddingEnd.dp
                ),
            onLikeClick = onLikeClick,
            onShareClick = onShareClick,
        )
    }
}