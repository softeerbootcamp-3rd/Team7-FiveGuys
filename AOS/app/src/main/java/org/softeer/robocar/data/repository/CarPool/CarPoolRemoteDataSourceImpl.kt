package org.softeer.robocar.data.repository.CarPool

import org.softeer.robocar.data.dto.carpool.request.RequestCarPoolRequest
import org.softeer.robocar.data.dto.carpool.request.registerCarPoolRequest
import org.softeer.robocar.data.dto.carpool.response.AcceptCarPoolResponse
import org.softeer.robocar.data.mapper.toCarPools
import org.softeer.robocar.data.model.CarPools
import org.softeer.robocar.data.service.CarPool.CarPoolService
import retrofit2.http.Header
import javax.inject.Inject

class CarPoolRemoteDataSourceImpl @Inject constructor(
    private val carPoolService: CarPoolService
) : CarPoolRemoteDataSource {

    override suspend fun getCarPoolList(
        guestStartLocation: String,
        guestDestinationLocation: String,
        countOfMen: Int,
        countOfFemale: Int,
        token: String,
    ): CarPools {
        return carPoolService.getCarPoolList(
            guestStartLocation,
            guestDestinationLocation,
            countOfMen,
            countOfFemale,
            token,
        ).toCarPools()
    }

    override suspend fun requestCarPool(
        request: RequestCarPoolRequest,
        token: String,
    ): Result<Unit> {
        return runCatching {
            carPoolService.requestCarPool(
                request,
                token,
            )
        }
    }

    override suspend fun registerCarPool(
        request: registerCarPoolRequest,
        token: String,
    ): Result<Unit> {
        return runCatching {
            carPoolService.registerCarPool(
                request,
                token,
            )
        }
    }

    override suspend fun rejectCarPoolRequest(
        guestId: Long,
        token: String,
    ): Result<Unit> {
        return runCatching {
            carPoolService.rejectCarPoolRequest(
                guestId,
                token,
            )
        }
    }

    override suspend fun acceptCarPoolRequest(
        guestId: Long,
        guestDestination: String,
        token: String,
    ): Result<AcceptCarPoolResponse> {
        return runCatching {
            carPoolService.acceptCarPoolRequest(
                guestId = guestId,
                guestDestination =  guestDestination,
                token= token,
            )
        }
    }

    override suspend fun checkOperationStatus(
        carPoolId: Long,
        token: String
    ): Result<Unit>{
        return runCatching {
            carPoolService.checkOperationStatus(carPoolId, token)
        }
    }
}