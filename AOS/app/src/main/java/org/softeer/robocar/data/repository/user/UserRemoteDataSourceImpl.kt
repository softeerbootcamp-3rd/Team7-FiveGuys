package org.softeer.robocar.data.repository.user

import org.softeer.robocar.data.dto.user.request.SignUpRequest
import org.softeer.robocar.data.dto.user.response.CheckAvailableNicknameResponse
import org.softeer.robocar.data.dto.user.response.CheckAvailableUserIdResponse
import org.softeer.robocar.data.service.user.UserService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserRemoteDataSource {

    override suspend fun checkAvailableUserId(
        userId: String
    ): Result<CheckAvailableUserIdResponse> {
        return runCatching {
            userService.checkAvailableUserId(userId)
        }
    }

    override suspend fun checkAvailableNickname(
        nickname: String
    ): Result<CheckAvailableNicknameResponse> {
        return runCatching {
            userService.checkAvailableNickname(nickname)
        }
    }

    override suspend fun signUp(
        signUpRequest: SignUpRequest
    ): Result<Unit> {
        return runCatching {
            userService.signUp(signUpRequest)
        }
    }
}