package org.softeer.robocar.data.repository.CarPool

import org.softeer.robocar.data.model.CarPools
import javax.inject.Inject

class CarPoolRepositoryImpl @Inject constructor(
    private val dataSource: CarPoolRemoteDataSource,
) : CarPoolRepository {

    override suspend fun getCarPoolList(
        startLocation: String,
        destinationLocation: String,
    ): CarPools {

        return dataSource.getCarPoolList(
            guestStartLocation = startLocation,
            guestDestinationLocation = destinationLocation
        )
    }


}