package com.example.biddexnewsapp.domain.usecase

import com.example.biddexnewsapp.domain.entity.NewEntity
import com.example.biddexnewsapp.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
){

    suspend operator fun invoke(): List<NewEntity> {
        return newsRepository.getNewsList()
    }
}