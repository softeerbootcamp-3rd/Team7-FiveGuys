package org.softeer.robocar.data.repository.user

import org.softeer.robocar.data.dto.user.request.SignUpRequest
import org.softeer.robocar.data.dto.user.response.CheckAvailableNicknameResponse
import org.softeer.robocar.data.dto.user.response.CheckAvailableUserIdResponse
import org.softeer.robocar.data.network.ApiResult
import org.softeer.robocar.data.network.handleApi
import org.softeer.robocar.data.service.user.UserService
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserRemoteDataSource {

    override suspend fun checkAvailableUserId(
        userId: String
    ): Response<CheckAvailableUserIdResponse> {
        return userService.checkAvailableUserId(userId)
    }

    override suspend fun checkAvailableNickname(
        nickname: String
    ): Response<CheckAvailableNicknameResponse> {
        return userService.checkAvailableNickname(nickname)
    }

    override suspend fun signUp(
        signUpRequest: SignUpRequest
    ): Response<Unit> {
        return userService.signUp(signUpRequest)
    }
}