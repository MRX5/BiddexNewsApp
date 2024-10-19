package com.example.biddexnewsapp.data.resource

import com.example.biddexnewsapp.domain.entity.NewEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewResource(
    val title: String? = "",
    val description: String? = "",
    val publishedAt: String? = "",
    val urlToImage: String? = "",
    val author: String? = "",
    val content: String? = ""
) {
    fun toNewEntity() = NewEntity(
        title = title ?: "",
        description = description ?: "",
        publishedAt = publishedAt ?: "",
        image = urlToImage ?: "",
        author = author ?: "",
    )
}
