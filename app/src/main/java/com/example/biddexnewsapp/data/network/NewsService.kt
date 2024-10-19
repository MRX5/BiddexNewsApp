package com.example.biddexnewsapp.data.network

import com.example.biddexnewsapp.domain.entity.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines?country=us")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<NewsResponse>
}