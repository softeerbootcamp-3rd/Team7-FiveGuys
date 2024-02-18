package org.softeer.robocar.data.repository.CarPool

import org.softeer.robocar.data.mapper.toCarPools
import org.softeer.robocar.data.model.CarPools
import org.softeer.robocar.data.service.CarPool.CarPoolService
import javax.inject.Inject

class CarPoolRemoteDataSourceImpl @Inject constructor(
    private val carPoolService: CarPoolService
) : CarPoolRemoteDataSource {

    override suspend fun getCarPoolList(
        guestStartLocation: String,
        guestDestinationLocation: String
    ): CarPools {
        return carPoolService.getCarPoolList(
            guestStartLocation,
            guestDestinationLocation
        ).toCarPools()
    }
}