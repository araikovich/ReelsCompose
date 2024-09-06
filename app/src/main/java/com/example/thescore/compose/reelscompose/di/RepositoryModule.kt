package com.example.thescore.compose.reelscompose.di

import com.example.thescore.compose.reelscompose.domain.repository.ReelsRepository
import com.example.thescore.compose.reelscompose.data.repository.ReelsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideReelsRepository(reelsRepositoryImpl: ReelsRepositoryImpl): ReelsRepository
}