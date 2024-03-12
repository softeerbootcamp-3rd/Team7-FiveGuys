package org.softeer.robocar.data.repository.user

import org.softeer.robocar.data.dto.user.request.SignUpRequest
import org.softeer.robocar.data.dto.user.response.CheckAvailableNicknameResponse
import org.softeer.robocar.data.dto.user.response.CheckAvailableUserIdResponse
import org.softeer.robocar.data.network.ApiResult
import retrofit2.Response

interface UserRemoteDataSource {

    suspend fun checkAvailableUserId(
        userId: String
    ): Response<CheckAvailableUserIdResponse>

    suspend fun checkAvailableNickname(
        nickname: String
    ): Response<CheckAvailableNicknameResponse>

    suspend fun signUp(
        signUpRequest: SignUpRequest
    ): Response<Unit>

}