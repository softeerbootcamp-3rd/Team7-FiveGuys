package org.softeer.robocar.data.service.user

import org.softeer.robocar.data.dto.user.request.SignUpRequest
import org.softeer.robocar.data.dto.user.response.CheckAvailableNicknameResponse
import org.softeer.robocar.data.dto.user.response.CheckAvailableUserIdResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @GET("users/loginId-validation")
    suspend fun checkAvailableUserId(
        @Query("loginId") userId: String
    ): Response<CheckAvailableUserIdResponse>

    @GET("users/nickname-validation")
    suspend fun checkAvailableNickname(
        @Query("nickname") nickname: String
    ): Response<CheckAvailableNicknameResponse>

    @POST("users")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): Response<Unit>
}