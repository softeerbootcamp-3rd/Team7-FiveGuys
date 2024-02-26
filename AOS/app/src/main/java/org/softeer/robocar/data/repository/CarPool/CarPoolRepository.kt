package org.softeer.robocar.data.repository.CarPool

import org.softeer.robocar.data.dto.carpool.request.registerCarPoolRequest
import org.softeer.robocar.data.dto.carpool.response.AcceptCarPoolResponse
import org.softeer.robocar.data.model.CarPool
import org.softeer.robocar.data.model.CarPools

interface CarPoolRepository {

    suspend fun getCarPoolList(
        startLocation: String,
        destinationLocation: String,
        countOfMen: Int,
        countOfFemale: Int,
    ): CarPools

    suspend fun requestCarPool(
        carPool: CarPool,
        guestDestinationLocation: String,
    ): Result<Unit>

    suspend fun registerCarPool(
        request: registerCarPoolRequest,
    ): Result<Unit>

    suspend fun rejectCarPoolRequest(
        guestId: Long,
    ): Result<Unit>

    suspend fun acceptCarPoolRequest(
        guestId: Long,
        guestDestination: String,
    ): Result<AcceptCarPoolResponse>

}