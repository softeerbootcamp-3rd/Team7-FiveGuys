package org.softeer.robocar.domain.usecase

import org.softeer.robocar.data.dto.user.request.SignUpRequest
import org.softeer.robocar.data.repository.user.UserRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(request: SignUpRequest): Boolean {
        return userRepository.signUp(request)
            .isSuccess
    }
}