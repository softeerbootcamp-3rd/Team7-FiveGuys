package org.softeer.robocar.data.repository.CarPool

import org.softeer.robocar.data.dto.carpool.request.RejectCarPoolRequest
import org.softeer.robocar.data.dto.carpool.request.RequestCarPoolRequest
import org.softeer.robocar.data.dto.carpool.request.registerCarPoolRequest
import org.softeer.robocar.data.dto.carpool.response.AcceptCarPoolResponse
import org.softeer.robocar.data.model.CarPools

interface CarPoolRemoteDataSource {

    suspend fun getCarPoolList(
        guestStartLocation: String,
        guestDestinationLocation: String,
        countOfMen: Int,
        countOfFemale: Int,
        token: String,
    ): CarPools

    suspend fun requestCarPool(
        request: RequestCarPoolRequest,
        token: String,
    ): Result<Unit>

    suspend fun registerCarPool(
        request: registerCarPoolRequest,
        token: String,
    ): Result<Unit>

    suspend fun rejectCarPoolRequest(
        guestId: Long,
        token: String,
    ): Result<Unit>

    suspend fun acceptCarPoolRequest(
        guestId: Long,
        guestDestination: String,
        token: String,
    ): Result<AcceptCarPoolResponse>
}