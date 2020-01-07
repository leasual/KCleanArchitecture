package com.geekdroid.kcleanarchitecture.core.model

/**
 * Create by james.li on 2019/12/19
 * Description:
 */

/*
sealed class Result<out T : Any>

uiModel class Success<out T : Any>(val uiModel: T) : Result<T>()
uiModel class Failure(val httpError: HttpError) : Result<Nothing>()

class HttpError(val throwable: Throwable, val errorCode: Int = 0)

inline fun <T : Any> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (this is Success) action(uiModel)
    return this
}

inline fun <T : Any> Result<T>.onFailure(action: (HttpError) -> Unit) {
    if (this is Failure) action(httpError)
}*/

sealed class Failure {
    data class NetworkError(val error: String = "network error") : Failure()
    data class NoNetworkError(val error: String = "no network error") : Failure()
    data class ServerError(val throwable: Throwable, val errorCode: Int = 0) : Failure()
    data class UnKnownError(val error: String = "unknown error") : Failure()
    data class DatabaseError(val error: String = "db error") : Failure()
}
