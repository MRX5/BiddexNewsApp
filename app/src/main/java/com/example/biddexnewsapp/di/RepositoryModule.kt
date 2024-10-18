package com.example.biddexnewsapp.di

import com.example.biddexnewsapp.data.network.NewsService
import com.example.biddexnewsapp.data.repo_impl.NewsRepositoryImpl
import com.example.biddexnewsapp.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepo(
        newsService: NewsService
    ): NewsRepository = NewsRepositoryImpl(
        newsService
    )


}