package org.softeer.robocar.domain.usecase

import org.softeer.robocar.data.dto.user.response.CheckAvailableNicknameResponse
import org.softeer.robocar.data.repository.user.UserRepository
import javax.inject.Inject


class CheckDuplicatedNicknameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(nickname: String): Result<CheckAvailableNicknameResponse> {
        return userRepository.checkAvailableNickname(nickname)
    }
}