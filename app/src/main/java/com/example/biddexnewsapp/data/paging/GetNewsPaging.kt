package com.example.biddexnewsapp.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.biddexnewsapp.data.network.NewsService
import com.example.biddexnewsapp.data.utils.handlePagingError
import com.example.biddexnewsapp.data.utils.handlePagingResponse
import com.example.biddexnewsapp.domain.entity.NewEntity
import com.example.biddexnewsapp.domain.entity.NewsResponse
import com.example.biddexnewsapp.domain.utils.NetworkException
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class GetNewsPaging(private val endPoint: NewsService) : PagingSource<Int, NewEntity>() {

    override fun getRefreshKey(state: PagingState<Int, NewEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewEntity> {
        return try {
            val pageIndex = params.key ?: 1
            val response = endPoint.getNews(
                page = pageIndex,
                pageSize = params.loadSize
            )

            if (response.isSuccessful) {
                return handlePagingResponse(
                    pageIndex,
                    response,
                    response.body()?.articles?.map { it.toNewEntity() },
                )
            } else {
                val error = response.errorBody()?.charStream()
                val jsonString = error?.buffered().use { it?.readText() }
                if(jsonString.isNullOrBlank()) throw Exception()
                val json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
                val errorMessage = json.decodeFromString<NewsResponse>(jsonString).message ?: ""
                when (response.code()) {
                    400 -> {
                        LoadResult.Error(NetworkException.BadRequestException(errorMessage))
                    }
                    401 -> {
                        LoadResult.Error(NetworkException.UnAuthorizedException(errorMessage))
                    }
                    429 -> {
                        LoadResult.Error(NetworkException.TooManyRequestsException(errorMessage))
                    }
                    500 -> {
                        LoadResult.Error(NetworkException.ServerErrorException(errorMessage))
                    }
                    else -> {
                        LoadResult.Error(NetworkException.UnknownException(errorMessage))

                    }
                }
            }

        } catch (exception: Exception) {
            handlePagingError(exception)
        }
    }
}
