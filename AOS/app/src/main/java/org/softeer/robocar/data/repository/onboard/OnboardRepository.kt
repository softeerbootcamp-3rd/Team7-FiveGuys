package org.softeer.robocar.data.repository.onboard

import org.softeer.robocar.data.dto.operation.OnboardData
import org.softeer.robocar.data.dto.operation.OnboardResponse
import org.softeer.robocar.data.model.Onboard

interface OnboardRepository {
    suspend fun getOnboardDetails(
        inOperationId: Int
    ): Result<OnboardData>
}
