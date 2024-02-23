package org.softeer.robocar.data.repository.CarPool

import org.softeer.robocar.data.dto.carpool.request.RequestCarPoolRequest
import org.softeer.robocar.data.model.CarPools

interface CarPoolRemoteDataSource {

    suspend fun getCarPoolList(
        guestStartLocation: String,
        guestDestinationLocation: String,
    ): CarPools

    suspend fun requestCarPool(
        request: RequestCarPoolRequest
    ): Result<Unit>

}