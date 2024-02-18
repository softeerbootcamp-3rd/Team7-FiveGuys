package org.softeer.robocar.data.repository.CarPool

import org.softeer.robocar.data.model.CarPools

interface CarPoolRemoteDataSource {

    suspend fun getCarPoolList(
        guestStartLocation: String,
        guestDestinationLocation: String,
    ): CarPools

}