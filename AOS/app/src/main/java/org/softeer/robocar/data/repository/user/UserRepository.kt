package org.softeer.robocar.data.repository.user

import org.softeer.robocar.data.dto.user.request.SignUpRequest
import org.softeer.robocar.data.network.ApiResult

interface UserRepository {

    suspend fun checkAvailableUserId(
        userId: String
    ): ApiResult

    suspend fun checkAvailableNickname(
        nickname: String
    ): ApiResult

    suspend fun signUp(
        signUpRequest: SignUpRequest
    ): ApiResult

}