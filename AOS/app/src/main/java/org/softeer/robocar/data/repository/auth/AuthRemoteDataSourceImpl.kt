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
    ): LoginResponse {
        return authService.login(loginRequest)
    }

}