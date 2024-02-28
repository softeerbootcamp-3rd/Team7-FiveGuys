package org.softeer.robocar.data.repository.onboard

import org.softeer.robocar.data.dto.operation.OnboardData
import org.softeer.robocar.data.service.operation.OnboardService
import org.softeer.robocar.di.RoboCarApplication.Companion.token
import javax.inject.Inject

class OnboardRemoteDataSourceImpl @Inject constructor(
    private val onboardService: OnboardService
): OnboardRemoteDataSource {

    override suspend fun getOnboard(
        inOperationId: Long,
        token: String
    ): Result<OnboardData> {
        return runCatching {
            onboardService.getOnboardDetails(inOperationId,token)
        }
    }
}