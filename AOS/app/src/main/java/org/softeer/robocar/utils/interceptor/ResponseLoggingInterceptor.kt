package org.softeer.robocar.utils.interceptor

import android.util.Log
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.softeer.robocar.data.dto.BaseResponse

class ResponseLoggingInterceptor : Interceptor {

    override fun intercept(chain: Chain): Response {
        val response = chain.proceed(chain.request())
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }
        var responseBody : BaseResponse<JsonElement>? = null
        val rawResponseBody = response.body?.string()

        try{
            val rawRequestBody = response.request.toString()
            Log.d(TAG, "------------------------------------------")
            Log.d(TAG, rawRequestBody)
            Log.d(TAG, "------------------------------------------")
        }
        catch (e : Exception){
            Log.d(TAG, e.message.toString())
            Log.d(TAG, "------------------------------------------")
        }
        runCatching {
            responseBody = json.decodeFromString<BaseResponse<JsonElement>>(rawResponseBody!!)
        }.onSuccess {
            Log.d(TAG, "------------------------------------------")
            Log.d(TAG, "Response Log: ")
            Log.d(TAG, "status: " + response.code)
            Log.d(TAG, "message: " + responseBody?.message)
            Log.d(TAG, "data: " + json.encodeToString(responseBody?.data))
            Log.d(TAG, "------------------------------------------")
        }.onFailure {
            Log.d(TAG, "------------------------------------------")
            Log.d(TAG, "Response Log: ")
            Log.d(TAG, "status: " + response.code)
            Log.d(TAG, "body: $rawResponseBody")
            Log.d(TAG, "------------------------------------------")
        }

        return response.newBuilder()
            .body(rawResponseBody?.toResponseBody(response.body?.contentType()))
            .build()
    }

    companion object {
        const val TAG = "API Response Log"
    }
}