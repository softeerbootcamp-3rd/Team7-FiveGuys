package org.softeer.robocar.domain.usecase

import org.softeer.robocar.data.dto.carpool.response.AcceptCarPoolResponse
import org.softeer.robocar.data.repository.CarPool.CarPoolRepository
import javax.inject.Inject

class AcceptCarPoolRequestUseCase @Inject constructor(
    private val carPoolRepository: CarPoolRepository
) {

    suspend operator fun invoke(
        guestId: Long,
        guestDestination: String
    ): Result<AcceptCarPoolResponse>{
        return carPoolRepository.acceptCarPoolRequest(
            guestId,
            guestDestination
        )
    }
}