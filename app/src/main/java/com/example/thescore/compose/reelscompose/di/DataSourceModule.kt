package com.example.thescore.compose.reelscompose.di

import com.example.thescore.compose.reelscompose.data.datasource.ReelsNetworkDataSource
import com.example.thescore.compose.reelscompose.data.datasource.ReelsNetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideReelsNetworkDataSource(reelsNetworkDataSource: ReelsNetworkDataSourceImpl): ReelsNetworkDataSource
}