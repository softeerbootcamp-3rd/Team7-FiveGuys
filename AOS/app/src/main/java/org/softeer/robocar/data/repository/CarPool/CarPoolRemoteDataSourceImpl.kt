package org.softeer.robocar.data.repository.CarPool

import org.softeer.robocar.data.dto.carpool.request.RequestCarPoolRequest
import org.softeer.robocar.data.dto.carpool.request.registerCarPoolRequest
import org.softeer.robocar.data.mapper.toCarPools
import org.softeer.robocar.data.model.CarPools
import org.softeer.robocar.data.service.CarPool.CarPoolService
import javax.inject.Inject

class CarPoolRemoteDataSourceImpl @Inject constructor(
    private val carPoolService: CarPoolService
) : CarPoolRemoteDataSource {

    override suspend fun getCarPoolList(
        guestStartLocation: String,
        guestDestinationLocation: String,
        countOfMen: Int,
        countOfFemale: Int,
    ): CarPools {
        return carPoolService.getCarPoolList(
            guestStartLocation,
            guestDestinationLocation,
            countOfMen,
            countOfFemale,
        ).toCarPools()
    }

    override suspend fun requestCarPool(
        request: RequestCarPoolRequest
    ): Result<Unit> {
        return runCatching {
            carPoolService.requestCarPool(
                request
            )
        }
    }

    override suspend fun registerCarPool(
        request: registerCarPoolRequest
    ): Result<Unit> {
        return runCatching {
            carPoolService.registerCarPool(
                request
            )
        }
    }
}