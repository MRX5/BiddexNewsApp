package com.example.biddexnewsapp.data.repo_impl

import com.example.biddexnewsapp.data.network.NewsService
import com.example.biddexnewsapp.data.utils.tryToExecute
import com.example.biddexnewsapp.domain.entity.NewEntity
import com.example.biddexnewsapp.domain.entity.NewsResponse
import com.example.biddexnewsapp.domain.repository.NewsRepository
import com.example.biddexnewsapp.domain.utils.DataState
import com.example.biddexnewsapp.domain.utils.NetworkException
import javax.inject.Inject

class NewsRepositoryImpl  @Inject constructor(
    private val newsEndPoint: NewsService
): NewsRepository {

    override suspend fun getNewsList() = tryToExecute {
        newsEndPoint.getNews()
    }.articles?.map { it.toNewEntity()} ?: throw NetworkException.NotFoundException
}