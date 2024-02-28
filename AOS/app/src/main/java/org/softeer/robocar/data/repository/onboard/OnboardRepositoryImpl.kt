package org.softeer.robocar.data.repository.onboard

import org.softeer.robocar.data.dto.operation.OnboardData
import org.softeer.robocar.data.dto.operation.OnboardResponse
import org.softeer.robocar.data.model.Onboard
import org.softeer.robocar.data.repository.auth.AuthLocalDataSource
import javax.inject.Inject

class OnboardRepositoryImpl @Inject constructor(
    private val onboardRemoteDataSource: OnboardRemoteDataSource,
    private val localAuthDataSource: AuthLocalDataSource,
) : OnboardRepository {

    override suspend fun getOnboardDetails(inOperationId: Long): Result<OnboardData> {
        return onboardRemoteDataSource.getOnboard(
            inOperationId,
            localAuthDataSource.getToken()
        )
    }
}
