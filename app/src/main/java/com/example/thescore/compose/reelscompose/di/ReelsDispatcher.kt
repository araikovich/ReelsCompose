package com.example.thescore.compose.reelscompose.di

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val dispatcher: ReelsDispatchers)

enum class ReelsDispatchers {
    Default,
    IO,
}