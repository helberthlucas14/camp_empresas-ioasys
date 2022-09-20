package com.example.empresas.data.data_remote

sealed class ResultWrapper<out T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : ResultWrapper<T>(data)
    class Failure(error: Throwable) : ResultWrapper<Nothing>(error = error)
}
