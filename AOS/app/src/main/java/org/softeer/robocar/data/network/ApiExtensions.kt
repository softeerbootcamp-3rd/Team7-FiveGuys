package org.softeer.robocar.data.network

import retrofit2.HttpException
import retrofit2.Response

suspend fun <T : Any> handleApi(
    execute: suspend () -> Response<T>
): ApiResult {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            ApiResult.Success(body)
        } else {
            ApiResult.Failure(code = response.code(), message = response.message())
        }
    } catch (e: HttpException) {
        ApiResult.Failure(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        ApiResult.Exception(e)
    }
}