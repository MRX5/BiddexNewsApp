package com.example.biddexnewsapp.domain.entity

import com.example.biddexnewsapp.data.resource.NewResource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val status: String? = "",
    val totalResults: Int? = 0,
    val articles: List<NewResource>? = null,
    val message: String? = "",
)