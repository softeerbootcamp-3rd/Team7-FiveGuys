package org.softeer.robocar.data.repository.auth

import kotlinx.coroutines.flow.first
import org.softeer.robocar.data.dto.login.request.LoginRequest
import org.softeer.robocar.data.dto.login.response.LoginResponse
import org.softeer.robocar.data.model.User
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource
) : AuthRepository {

    override suspend fun login(
        loginRequest: LoginRequest
    ): Result<LoginResponse> {
        return authRemoteDataSource.login(loginRequest)
    }

    override suspend fun saveToken(
        token: String
    ) {
        authLocalDataSource.saveToken(token)
    }

    override suspend fun saveUserInfo(
        user: User
    ) {
        authLocalDataSource.saveUserInfo(user)
    }

    override suspend fun getUserInfo(): User {
        return authLocalDataSource.getUserInfo().first()
    }

    override suspend fun verifyUserToken(
    ): Result<Unit> {
        return authRemoteDataSource.verifyUserToken(
            authLocalDataSource.getToken()
        )
    }

}