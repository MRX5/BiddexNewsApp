package com.example.biddexnewsapp.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.biddexnewsapp.domain.entity.NewEntity
import com.example.biddexnewsapp.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
){

    operator fun invoke() = Pager(
        config = PagingConfig(
            pageSize = 20,
            initialLoadSize = 20,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            newsRepository.getNewsList()
        }
    )
}