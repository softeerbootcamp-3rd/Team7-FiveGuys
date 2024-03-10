package org.softeer.robocar.data.network

sealed class ApiResult<out T> {

    object Loading : ApiResult<Nothing>()
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Failure(val message: String?) : ApiResult<Nothing>()
    data class Exception(val e: Throwable) : ApiResult<Nothing>()

}