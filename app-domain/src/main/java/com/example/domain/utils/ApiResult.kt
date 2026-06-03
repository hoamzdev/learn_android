package com.example.domain.utils

data class DataError(
    val code: Int?,
    val message: String?,
    val errorBody: String? = null,
)

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val dataError: DataError) :
        ApiResult<Nothing>()
}

fun <T> ApiResult<T>.onSuccess(
    block: (T) -> Unit,
): ApiResult<T> {
    if (this is ApiResult.Success) {
        block(data)
    }
    return this
}

fun <T> ApiResult<T>.onError(
    block: (DataError) -> Unit,
): ApiResult<T> {
    if (this is ApiResult.Error) {
        block(dataError)
    }
    return this
}