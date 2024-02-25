package org.softeer.robocar.data.repository.onboard

import org.softeer.robocar.data.dto.operation.OnboardData
import org.softeer.robocar.data.dto.operation.OnboardResponse
import org.softeer.robocar.data.model.Onboard
import javax.inject.Inject

class OnboardRepositoryImpl @Inject constructor(
    private val onboardRemoteDataSource: OnboardRemoteDataSource
) : OnboardRepository {

    override suspend fun getOnboardDetails(inOperationId: Int): Result<OnboardData> {
        return onboardRemoteDataSource.getOnboard(inOperationId)
    }
}
