package org.softeer.robocar.data.repository.auth

import org.softeer.robocar.data.dto.login.request.LoginRequest
import org.softeer.robocar.data.dto.login.response.LoginResponse
import org.softeer.robocar.data.service.auth.AuthService
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {

    override suspend fun login(
        loginRequest: LoginRequest
    ): Result<LoginResponse> {
        return runCatching {
            authService.login(loginRequest)
        }
    }

    override suspend fun verifyUserToken(
        token: String
    ): Result<Unit> {
        return runCatching {
            authService.verifyUserToken(token)
        }
    }

}