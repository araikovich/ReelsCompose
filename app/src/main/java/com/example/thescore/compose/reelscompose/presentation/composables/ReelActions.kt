package com.example.thescore.compose.reelscompose.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.thescore.compose.reelscompose.R
import com.example.thescore.compose.reelscompose.presentation.model.ReelUiModel

private const val TextPaddingTop = 4
private const val ImagePadding = 32
private const val ImageSize = 48

@Composable
fun ReelActions(
    item: ReelUiModel,
    modifier: Modifier,
    onLikeClick: (String) -> Unit,
    onShareClick: (String) -> Unit,
) {
    Column(modifier = modifier) {
        Icon(
            painter = painterResource(id = R.drawable.ic_like),
            contentDescription = "Like",
            tint = if (item.isLiked) Color.Red else Color.White,
            modifier = Modifier
                .size(ImageSize.dp)
                .clickable { onLikeClick(item.id) },
        )
        Text(
            text = item.likesCount.toString(),
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .align(
                    Alignment.CenterHorizontally
                )
                .padding(top = TextPaddingTop.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_share),
            contentDescription = "Like",
            tint = Color.White,
            modifier = Modifier
                .padding(top = ImagePadding.dp)
                .size(ImageSize.dp)
                .clickable { onShareClick(item.id) }
        )
        Text(
            text = item.shareCount.toString(),
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .align(
                    Alignment.CenterHorizontally
                )
                .padding(top = TextPaddingTop.dp)
        )
    }
}