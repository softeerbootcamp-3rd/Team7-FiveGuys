package org.softeer.robocar.domain.usecase

import org.softeer.robocar.data.network.ApiResult
import org.softeer.robocar.data.repository.user.UserRepository
import javax.inject.Inject

class CheckDuplicatedUserIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String): ApiResult {
        return userRepository.checkAvailableUserId(userId)
    }
}