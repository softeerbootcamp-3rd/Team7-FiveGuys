package org.softeer.robocar.domain.usecase

import org.softeer.robocar.data.dto.user.request.SignUpRequest
import org.softeer.robocar.data.network.ApiResult
import org.softeer.robocar.data.repository.user.UserRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(request: SignUpRequest):ApiResult {
        return userRepository.signUp(request)
    }
}