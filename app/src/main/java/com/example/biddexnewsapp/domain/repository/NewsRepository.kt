package com.example.biddexnewsapp.domain.repository

import com.example.biddexnewsapp.domain.entity.NewEntity
import com.example.biddexnewsapp.domain.utils.DataState

interface NewsRepository {
    suspend fun getNewsList(): List<NewEntity>
}