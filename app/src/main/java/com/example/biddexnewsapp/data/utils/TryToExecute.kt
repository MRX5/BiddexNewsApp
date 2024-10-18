package com.example.biddexnewsapp.data.utils

import android.util.Log
import com.example.biddexnewsapp.domain.utils.NetworkException
import retrofit2.Response
import java.io.IOException

suspend fun <T> tryToExecute(func: suspend () -> Response<T>): T {
    val response = func()
    Log.d("TAG", "tryToExecute: ${response.code()}")
    if (response.isSuccessful) {
        return response.body() ?: throw NetworkException.NotFoundException
    }
    throw when (response.code()) {
        400 -> NetworkException.BadRequestException
        401 -> NetworkException.UnAuthorizedException
        408 -> NetworkException.TimeOutException
        429 -> NetworkException.TooManyRequestsException
        500 -> NetworkException.ServerErrorException
        else -> IOException()
    }
}