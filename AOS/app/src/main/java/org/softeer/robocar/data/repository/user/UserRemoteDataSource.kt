package org.softeer.robocar.data.repository.user

import org.softeer.robocar.data.dto.user.request.SignUpRequest
import org.softeer.robocar.data.dto.user.response.CheckAvailableNicknameResponse
import org.softeer.robocar.data.dto.user.response.CheckAvailableUserIdResponse

interface UserRemoteDataSource {

    suspend fun checkAvailableUserId(
        userId: String
    ): Result<CheckAvailableUserIdResponse>

    suspend fun checkAvailableNickname(
        nickname: String
    ): Result<CheckAvailableNicknameResponse>

    suspend fun signUp(
        signUpRequest: SignUpRequest
    ): Result<Unit>

}