package org.softeer.robocar.data.repository.CarPool

import org.softeer.robocar.data.dto.carpool.request.RequestCarPoolRequest
import org.softeer.robocar.data.dto.carpool.request.registerCarPoolRequest
import org.softeer.robocar.data.model.CarPools

interface CarPoolRemoteDataSource {

    suspend fun getCarPoolList(
        guestStartLocation: String,
        guestDestinationLocation: String,
        countOfMen: Int,
        countOfFemale: Int,
    ): CarPools

    suspend fun requestCarPool(
        request: RequestCarPoolRequest
    ): Result<Unit>

    suspend fun registerCarPool(
        request: registerCarPoolRequest
    ): Result<Unit>

}