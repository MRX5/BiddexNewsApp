package com.example.biddexnewsapp.di

import com.example.biddexnewsapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@InstallIn(SingletonComponent::class)
@Module
object StringsModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseUrl

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ApiKey

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = BuildConfig.REMOTE_URL


    @Provides
    @ApiKey
    fun provideApiKey(): String = BuildConfig.API_KEY

}