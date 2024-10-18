package com.example.biddexnewsapp.domain.utils

sealed class NetworkException: Exception(){
    object BadRequestException: NetworkException()
    object NotFoundException: NetworkException()
    object TooManyRequestsException: NetworkException()
    object UnAuthorizedException: NetworkException()
    object TimeOutException: NetworkException()
    object ServerErrorException: NetworkException()
}