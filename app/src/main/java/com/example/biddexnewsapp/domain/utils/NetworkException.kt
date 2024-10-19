package com.example.biddexnewsapp.domain.utils

sealed class NetworkException: Exception(){
    data class BadRequestException(val msg: String): NetworkException()
    object NotFoundException: NetworkException()
    data class TooManyRequestsException(val msg: String): NetworkException()
    data class UnAuthorizedException(val msg: String): NetworkException()
    object TimeOutException: NetworkException()
    data class ServerErrorException(val msg: String): NetworkException()
    object ConnectionException: NetworkException()
    data class UnknownException(val msg: String): NetworkException()
    object IOException: NetworkException()
}