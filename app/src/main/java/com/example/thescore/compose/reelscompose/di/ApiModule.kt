package com.example.thescore.compose.reelscompose.di

import com.example.thescore.compose.reelscompose.data.api.ReelsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideApi(retrofit: Retrofit): ReelsApi {
        return retrofit.create(ReelsApi::class.java)
    }
}