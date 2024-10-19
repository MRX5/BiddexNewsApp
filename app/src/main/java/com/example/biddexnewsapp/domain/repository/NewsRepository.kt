package com.example.biddexnewsapp.domain.repository

import androidx.paging.PagingSource
import com.example.biddexnewsapp.domain.entity.NewEntity
import com.example.biddexnewsapp.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNewsList(): PagingSource<Int, NewEntity>
}