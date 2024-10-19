package com.example.biddexnewsapp.data.utils

import android.util.Log
import androidx.paging.PagingSource
import com.example.biddexnewsapp.domain.entity.NewEntity
import com.example.biddexnewsapp.domain.entity.NewsResponse
import com.example.biddexnewsapp.domain.utils.NetworkException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.io.Reader
import java.net.UnknownHostException

fun handlePagingResponse(
    pageIndex: Int,
    response: Response<NewsResponse>,
    list: List<NewEntity>?
): PagingSource.LoadResult<Int, NewEntity> {

    return try {
        val nextKey =
        if(list.isNullOrEmpty()){
            null
        }else {
            pageIndex + 1
        }

        val bodyMessage = response.body()?.message

        when (response.body()?.status) {
            "ok" -> {
                PagingSource.LoadResult.Page(
                        data = list!!,
                        prevKey = if (pageIndex == 1) null else pageIndex - 1,
                        nextKey = nextKey
                    )
                }
            else -> {
                when (response.code()) {
                    400 -> {
                        PagingSource.LoadResult.Error(
                            NetworkException.BadRequestException(bodyMessage ?: "BadRequestException")
                        )
                    }

                    401 -> {
                        PagingSource.LoadResult.Error(
                            NetworkException.UnAuthorizedException(
                                bodyMessage ?: "UnAuthorizedException"
                            )
                        )
                    }

                    429 -> {
                        PagingSource.LoadResult.Error(
                            NetworkException.TooManyRequestsException(
                                bodyMessage ?: "TooManyRequestsException"
                            )
                        )
                    }

                    500 -> {
                        PagingSource.LoadResult.Error(
                            NetworkException.ServerErrorException(
                                bodyMessage ?: "ServerErrorException"
                            )
                        )
                    }

                    else -> {
                        PagingSource.LoadResult.Error(NetworkException.UnknownException(bodyMessage ?: ""))

                    }
                }
            }
        }
    } catch (exception: Exception) {
        Log.e("TAG", "handlePagingResponse: $exception", )
        handlePagingError(exception)
    }
}


fun handlePagingError(throwable: Throwable): PagingSource.LoadResult<Int, NewEntity> {
    throwable.printStackTrace()
    return PagingSource.LoadResult.Error(
        when (throwable) {
            is TimeoutCancellationException -> NetworkException.TimeOutException
            is UnknownHostException -> NetworkException.ConnectionException
            is IOException -> NetworkException.IOException
            is HttpException -> convertErrorBody(throwable)
            else -> NetworkException.UnknownException(throwable.message ?: "")
        }
    )
}

fun convertErrorBody(throwable: HttpException): Exception {
    return try {
        val jsonString = getErrorBody(throwable.response()?.errorBody())
        if (!jsonString.isNullOrBlank()) {
            val response: NewsResponse = Json.decodeFromString(jsonString)
            NetworkException.UnknownException(response.message ?: "")
        } else {
            NetworkException.UnknownException(throwable.message ?: "")
        }
    } catch (e: Exception) {
        NetworkException.UnknownException(e.message ?: "")
    }
}

fun getErrorBody(errorBody: ResponseBody?): String? {
    val error = errorBody?.charStream()
    val jsonString = error?.buffered().use { it?.readText() }
    return jsonString
}