package com.example.biddexnewsapp.data.repo_impl

import androidx.paging.PagingSource
import com.example.biddexnewsapp.data.network.NewsService
import com.example.biddexnewsapp.data.paging.GetNewsPaging
import com.example.biddexnewsapp.domain.entity.NewEntity
import com.example.biddexnewsapp.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl  @Inject constructor(
    private val newsEndPoint: NewsService
): NewsRepository {

    override fun getNewsList(): PagingSource<Int, NewEntity> {
        return GetNewsPaging(newsEndPoint)
    }

    /*
    override suspend fun getNewsList() = tryToExecute {
        newsEndPoint.getNews()
    }.articles?.map { it.toNewEntity()} ?: throw NetworkException.NotFoundException
*/

}