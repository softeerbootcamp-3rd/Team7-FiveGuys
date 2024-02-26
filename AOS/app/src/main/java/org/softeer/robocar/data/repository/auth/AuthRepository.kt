package org.softeer.robocar.data.repository.auth

import org.softeer.robocar.data.dto.login.request.LoginRequest
import org.softeer.robocar.data.dto.login.response.LoginResponse
import org.softeer.robocar.data.model.User

interface AuthRepository {

    suspend fun login(
        loginRequest: LoginRequest
    ): Result<LoginResponse>

    suspend fun saveToken(
        token: String
    )

    suspend fun saveUserInfo(
        user: User
    )

    suspend fun getUserInfo(
    ): User

    suspend fun verifyUserToken(
    ): Result<Unit>

}