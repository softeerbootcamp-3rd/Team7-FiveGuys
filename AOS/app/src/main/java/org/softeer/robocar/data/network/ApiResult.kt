package org.softeer.robocar.data.network

sealed class ApiResult {

    object Loading : ApiResult()
    data class Success<out T>(val data: T) : ApiResult()
    data class Failure(val code: Int, val message: String?) : ApiResult()
    data class Exception(val e: Throwable) : ApiResult()

}