package org.softeer.robocar.domain.usecase

import org.softeer.robocar.data.dto.operation.OnboardData
import org.softeer.robocar.data.dto.operation.OnboardResponse
import org.softeer.robocar.data.repository.onboard.OnboardRepository
import javax.inject.Inject

class OnboardDetailsUseCase @Inject constructor(
    private val onboardRepository: OnboardRepository
) {
    suspend operator fun invoke(
        inOperationId: Int
    ): Result<OnboardData> { // 반환 타입을 Result<Onboard>로 변경
        return onboardRepository.getOnboardDetails(inOperationId)
    }
}
