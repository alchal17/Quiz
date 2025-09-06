package com.example.quiz.data.remote.api

sealed interface ApiCallResult<out T> {
    data class Success<out T>(val data: T) : ApiCallResult<T>
    data class HttpError(val code: Int, val message: String? = null) : ApiCallResult<Nothing>
}