package org.softeer.robocar.domain.usecase

import org.softeer.robocar.data.model.CarPool
import org.softeer.robocar.data.repository.CarPool.CarPoolRepository
import javax.inject.Inject

class RequestCarPoolUseCase @Inject constructor(
    private val repository: CarPoolRepository
) {
    suspend operator fun invoke(
        carPool: CarPool
    ): Result<Unit> {
        return repository.requestCarPool(carPool)
    }
}