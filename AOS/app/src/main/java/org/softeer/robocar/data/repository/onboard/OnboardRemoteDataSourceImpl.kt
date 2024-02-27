package org.softeer.robocar.data.repository.onboard

import org.softeer.robocar.data.dto.operation.OnboardData
import org.softeer.robocar.data.dto.operation.OnboardResponse
import org.softeer.robocar.data.service.operation.OnboardService
import javax.inject.Inject

class OnboardRemoteDataSourceImpl @Inject constructor(
    private val onboardService: OnboardService
): OnboardRemoteDataSource {

    override suspend fun getOnboard(
        inOperationId: Int
    ): Result<OnboardData> {
        return runCatching {
            onboardService.getOnboardDetails(inOperationId)
        }
    }
}