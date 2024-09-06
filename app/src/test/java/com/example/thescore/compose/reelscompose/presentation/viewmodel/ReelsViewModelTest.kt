@file:OptIn(ExperimentalTime::class)

package com.example.thescore.compose.reelscompose.presentation.viewmodel

import app.cash.turbine.test
import com.example.thescore.compose.reelscompose.data.util.ResultWrapper
import com.example.thescore.compose.reelscompose.domain.GetReelsUseCase
import com.example.thescore.compose.reelscompose.domain.ReelModel
import com.example.thescore.compose.reelscompose.domain.ReelsModel
import com.example.thescore.compose.reelscompose.presentation.model.ReelUiModel
import com.example.thescore.compose.reelscompose.presentation.model.ReelsUiModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class)
class ReelsViewModelTest {

    private lateinit var viewModel: ReelsViewModel
    private val getReelsUseCase = mockk<GetReelsUseCase>()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        val reelsData = ReelsModel(
            feedTitle = "Sample Title",
            reels = listOf(
                ReelModel(
                    id = "1",
                    description = "Sample Reel 1",
                    thumbnailUrl = "thumbnail1",
                    videoUrl = "video1",
                    likesCount = 10,
                    shareCount = 5,
                )
            )
        )

        coEvery { getReelsUseCase() } coAnswers {
            ResultWrapper.Success(reelsData)
        }
        viewModel = ReelsViewModel(getReelsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset main dispatcher after tests
    }

    @Test
    fun `getReels success updates state with reels data`() = runTest {
        viewModel.state.test {
            val expectedState = ReelsState(
                reels = ReelsUiModel(
                    title = "Sample Title",
                    reels = listOf(
                        ReelUiModel(
                            id = "1",
                            description = "Sample Reel 1",
                            thumbnailUrl = "thumbnail1",
                            videoUrl = "video1",
                            likesCount = 10,
                            shareCount = 5,
                            isLiked = false
                        )
                    )
                ),
                isLoading = false,
                isError = false
            )
            assertEquals(expectedState, awaitItem())
        }
    }

    @Test
    fun `getReels error updates state with error flag`() = runTest {
        coEvery { getReelsUseCase() } coAnswers {
            ResultWrapper.Error.ServerError("500", "Internal Server Error")
        }
        val subject = ReelsViewModel(getReelsUseCase)
        subject.state.test {
            val expectedState = ReelsState(
                reels = null,
                isLoading = false,
                isError = true
            )
            assertEquals(expectedState, awaitItem())
        }
    }

    @Test
    fun `onLike toggles the like state of a reel`() = runTest {
        val initialReelsData = ReelsModel(
            feedTitle = "Sample Title",
            reels = listOf(
                ReelModel(
                    id = "1",
                    description = "Sample Reel 1",
                    thumbnailUrl = "thumbnail1",
                    videoUrl = "video1",
                    likesCount = 10,
                    shareCount = 5,
                )
            )
        )

        coEvery { getReelsUseCase() } coAnswers {
            ResultWrapper.Success(initialReelsData)
        }

        viewModel = ReelsViewModel(getReelsUseCase)

        viewModel.state.test {
            awaitItem()
            viewModel.onLike("1")
            val updatedState = awaitItem()
            val updatedReel = updatedState.reels?.reels?.first()
            assertEquals(true, updatedReel?.isLiked)
            assertEquals(11, updatedReel?.likesCount)
            viewModel.onLike("1")
            val unlikedState = awaitItem()
            val unlikedReel = unlikedState.reels?.reels?.first()
            assertEquals(false, unlikedReel?.isLiked)
            assertEquals(10, unlikedReel?.likesCount) // Likes decremented
        }
    }
}
