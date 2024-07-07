package com.yapp.moa.domain.result

sealed class DataResult<out T> {
    data class Success<T>(
        val data: T
    ) : DataResult<T>()

    data class Failure(
        val throwable: Throwable
    ) : DataResult<Nothing>()

    object Loading : DataResult<Nothing>()

    object None : DataResult<Nothing>()
}
