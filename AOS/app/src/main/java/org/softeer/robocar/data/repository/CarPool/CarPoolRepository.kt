package org.softeer.robocar.data.repository.CarPool

import org.softeer.robocar.data.model.CarPools

interface CarPoolRepository {

    suspend fun getCarPoolList(
        startLocation: String,
        destinationLocation: String,
    ): CarPools

}