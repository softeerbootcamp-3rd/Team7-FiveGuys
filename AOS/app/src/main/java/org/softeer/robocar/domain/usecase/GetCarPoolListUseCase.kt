package org.softeer.robocar.domain.usecase

import org.softeer.robocar.data.model.CarPools
import org.softeer.robocar.data.repository.CarPool.CarPoolRepository
import javax.inject.Inject

class GetCarPoolListUseCase @Inject constructor(
    private val carPoolRepository: CarPoolRepository
) {
    suspend operator fun invoke(
        startLocation: String,
        destinationLocation: String,
        countOfMen: Int,
        countOfFemale: Int,
    ): CarPools {
        return carPoolRepository.getCarPoolList(
            startLocation,
            destinationLocation,
            countOfMen,
            countOfFemale,
        )
    }
}