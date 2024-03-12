package org.softeer.robocar.data.repository.user

import org.softeer.robocar.data.dto.user.request.SignUpRequest
import org.softeer.robocar.data.network.ApiResult
import org.softeer.robocar.data.network.handleApi
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun checkAvailableUserId(
        userId: String
    ): ApiResult {
        return handleApi {
            userRemoteDataSource.checkAvailableUserId(userId)
        }
    }

    override suspend fun checkAvailableNickname(
        nickname: String
    ): ApiResult {
        return handleApi {
            userRemoteDataSource.checkAvailableNickname(nickname)
        }
    }

    override suspend fun signUp(
        signUpRequest: SignUpRequest
    ): ApiResult {
        return handleApi {
            userRemoteDataSource.signUp(signUpRequest)
        }
    }
}