package org.softeer.robocar.domain.usecase

import org.softeer.robocar.data.repository.CarPool.CarPoolRepository
import javax.inject.Inject

class RejectCarPoolRequestUseCase @Inject constructor(
    private val carPoolRepository: CarPoolRepository
) {

    suspend operator fun invoke(
        guestId: Long
    ): Result<Unit> {
        return carPoolRepository.rejectCarPoolRequest(
            guestId
        )
    }
}