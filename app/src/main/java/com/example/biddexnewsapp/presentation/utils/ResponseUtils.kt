package com.example.biddexnewsapp.presentation.utils

import com.example.biddexnewsapp.domain.utils.NetworkException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext

/*
fun <V> tryToExecute(
    coroutineScope: CoroutineScope,
    callee: suspend () -> V,
    onSuccess: (V) -> Unit,
    onError: (NetworkException) -> Unit,
    dispatcher: CoroutineContext,
): Job {
    return coroutineScope.launch(dispatcher) {
        try {
            val result = callee()
            onSuccess(result)
        } catch (e: NetworkException.UnAuthorizedException) {
            onError(ErrorUIState.UnAuthorized)
        } catch (e: NetworkException.NoInternetException) {
            onError(ErrorUIState.NoInternet)
        } catch (e: UnknownHostException) {
            onError(ErrorUIState.NoInternet)
        } catch (e: NetworkException.TimeOutException) {
            onError(ErrorUIState.ConnectionTimeout)
        } catch (e: Exception) {
            onError(ErrorUIState.InternalServerError)
        }
    }
}*/
