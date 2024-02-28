package org.softeer.robocar.data.repository.onboard

import org.softeer.robocar.data.dto.operation.OnboardData

interface OnboardRemoteDataSource {
    suspend fun getOnboard(
        inOperationId: Long,
        token: String
    ): Result<OnboardData>
}
