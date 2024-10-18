package com.example.biddexnewsapp.data.network

import com.example.biddexnewsapp.domain.entity.NewsResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsService {

    @GET("top-headlines?country=us")
    suspend fun getNews(): Response<NewsResponse>

}