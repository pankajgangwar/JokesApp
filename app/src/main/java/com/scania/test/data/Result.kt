package com.scania.test.data

typealias ResultSuccess<T> = Result.Success<T>
typealias ResultError = Result.Error

sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: Failure) : Result<Nothing>()
    object Loading : Result<Nothing>()
}


fun <T> Result<T>.data(): T? {
    return when (this) {
        is ResultSuccess -> this.data
        else -> null
    }
}

fun <T> Result<T>.errorData(): Failure? {
    return when (this) {
        is ResultError -> this.error
        else -> null
    }
}

fun <T : Any> Result<T>.success(action: (T) -> Unit): Result<T> {
    if (this is ResultSuccess) data.let(action)
    return this
}


fun <T> Response<T>.toResult(): Result<T> {
    return when (this) {
        is ResponseSuccess -> {
            ResultSuccess(data)
        }
        is ResponseError -> {
            ResultError(error)
        }
    }
}