package org.softeer.robocar.domain.usecase

import org.softeer.robocar.data.dto.carpool.request.registerCarPoolRequest
import org.softeer.robocar.data.repository.CarPool.CarPoolRepository
import javax.inject.Inject

class RegisterCarPoolUseCase @Inject constructor(
    private val carPoolRepository: CarPoolRepository
) {

    suspend operator fun invoke(
        request: registerCarPoolRequest
    ): Result<Unit> {
        return carPoolRepository.registerCarPool(
            request
        )
    }
}