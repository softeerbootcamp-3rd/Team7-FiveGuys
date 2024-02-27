package org.softeer.robocar.data.repository.onboard

import org.softeer.robocar.data.dto.operation.OnboardData
import org.softeer.robocar.data.dto.operation.OnboardResponse
import org.softeer.robocar.data.model.Onboard

interface OnboardRemoteDataSource {
    suspend fun getOnboard(
        inOperationId: Int
    ): Result<OnboardData>
}
