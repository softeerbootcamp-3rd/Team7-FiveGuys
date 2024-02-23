package org.softeer.robocar.domain.usecase

import org.softeer.robocar.data.dto.carpool.request.RejectCarPoolRequest
import org.softeer.robocar.data.repository.CarPool.CarPoolRepository
import javax.inject.Inject

class RejectCarPoolRequestUseCase @Inject constructor(
    private val carPoolRepository: CarPoolRepository
) {

    suspend operator fun invoke(
        request: RejectCarPoolRequest
    ): Result<Unit> {
        return carPoolRepository.rejectCarPoolRequest(
            request
        )
    }
}