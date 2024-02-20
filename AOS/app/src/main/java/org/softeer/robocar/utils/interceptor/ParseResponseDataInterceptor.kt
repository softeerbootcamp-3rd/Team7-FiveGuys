package org.softeer.robocar.utils.interceptor

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.softeer.robocar.data.dto.BaseResponse

class ParseResponseDataInterceptor : Interceptor {
    override fun intercept(chain: Chain): Response {
        val response = chain.proceed(chain.request())
        val json = Json {
            ignoreUnknownKeys = true
        }
        val rawResponseBody = response.body!!.string()
        val responseBody = json.decodeFromString<BaseResponse<JsonElement>>(rawResponseBody)
        val data = responseBody.data

        if (data is Map<*, *>) {
            val newBody = data.toString().toResponseBody(response.body?.contentType())
            return response.newBuilder()
                .body(newBody)
                .build()
        }

        return response.newBuilder()
            .body(rawResponseBody.toResponseBody(response.body?.contentType()))
            .build()
    }
}