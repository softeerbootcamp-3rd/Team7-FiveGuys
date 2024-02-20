package org.softeer.robocar.domain.usecase

import android.util.Log
import org.softeer.robocar.data.dto.login.request.LoginRequest
import org.softeer.robocar.data.mapper.toUser
import org.softeer.robocar.data.repository.auth.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        request: LoginRequest
    ): Boolean {
        return authRepository.login(request)
            .onSuccess {
                authRepository.saveToken(it.token)
                authRepository.saveUserInfo(it.toUser())
            }
            .onFailure {
                Log.d("error", it.message.toString())
            }
            .isSuccess
    }
}