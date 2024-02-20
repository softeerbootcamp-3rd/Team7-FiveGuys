package org.softeer.robocar.data.repository.auth

import org.softeer.robocar.data.dto.login.request.LoginRequest
import org.softeer.robocar.data.dto.login.response.LoginResponse

interface AuthRemoteDataSource {

    suspend fun login(
        loginRequest: LoginRequest
    ): Result<LoginResponse>

}