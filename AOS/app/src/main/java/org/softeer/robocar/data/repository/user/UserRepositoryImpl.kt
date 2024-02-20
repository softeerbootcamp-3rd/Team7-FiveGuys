package org.softeer.robocar.data.repository.user

import org.softeer.robocar.data.dto.user.request.SignUpRequest
import org.softeer.robocar.data.dto.user.response.CheckAvailableNicknameResponse
import org.softeer.robocar.data.dto.user.response.CheckAvailableUserIdResponse
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun checkAvailableUserId(
        userId: String
    ): Result<CheckAvailableUserIdResponse> {
        return userRemoteDataSource.checkAvailableUserId(userId)
    }

    override suspend fun checkAvailableNickname(
        nickname: String
    ): Result<CheckAvailableNicknameResponse> {
        return userRemoteDataSource.checkAvailableNickname(nickname)
    }

    override suspend fun signUp(
        signUpRequest: SignUpRequest
    ): Result<Unit> {
        return userRemoteDataSource.signUp(signUpRequest)
    }
}