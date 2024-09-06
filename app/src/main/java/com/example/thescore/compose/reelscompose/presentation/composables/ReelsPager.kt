package com.example.thescore.compose.reelscompose.presentation.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.thescore.compose.reelscompose.presentation.model.ReelUiModel

@Composable
fun ReelsPager(
    items: List<ReelUiModel>,
    onLikeClick: (String) -> Unit,
    onShareClick: (String)  -> Unit,
    modifier: Modifier = Modifier
) {
    val pageCount = Int.MAX_VALUE
    val pagerState = rememberPagerState(pageCount = { pageCount }, initialPage = 0)
    LaunchedEffect(key1 = Unit) {
        var startIndex = pageCount / 2
        while (startIndex % items.size != 0) {
            startIndex++
        }
        pagerState.scrollToPage(startIndex)
    }

    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        snapAnimationSpec = tween(
            easing = LinearEasing,
            durationMillis = 300,
        ),
    )

    VerticalPager(
        flingBehavior = fling,
        state = pagerState,
        modifier = modifier.fillMaxSize(),
        beyondViewportPageCount = 1
    ) { page ->
        ReelPage(
            item = items[page % items.size],
            pagerState = pagerState,
            index = page,
            onLikeClick = onLikeClick,
            onShareClick = onShareClick,
        )
    }
}