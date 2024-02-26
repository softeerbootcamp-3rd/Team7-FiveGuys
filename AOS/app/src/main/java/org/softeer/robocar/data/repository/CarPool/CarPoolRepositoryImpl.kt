package org.softeer.robocar.data.repository.CarPool

import org.softeer.robocar.data.dto.carpool.request.registerCarPoolRequest
import org.softeer.robocar.data.dto.carpool.response.AcceptCarPoolResponse
import org.softeer.robocar.data.mapper.toRequestCarPool
import org.softeer.robocar.data.model.CarPool
import org.softeer.robocar.data.model.CarPools
import org.softeer.robocar.data.repository.auth.AuthLocalDataSource
import javax.inject.Inject

class CarPoolRepositoryImpl @Inject constructor(
    private val dataSource: CarPoolRemoteDataSource,
    private val localAuthDataSource: AuthLocalDataSource,
) : CarPoolRepository {

    override suspend fun getCarPoolList(
        startLocation: String,
        destinationLocation: String,
        countOfMen: Int,
        countOfFemale: Int,
    ): CarPools {

        return dataSource.getCarPoolList(
            guestStartLocation = startLocation,
            guestDestinationLocation = destinationLocation,
            countOfMen,
            countOfFemale,
            localAuthDataSource.getToken()
        )
    }

    override suspend fun requestCarPool(
        carPool: CarPool,
        guestDestinationLocation: String,
    ): Result<Unit> {
        return dataSource.requestCarPool(
            carPool.toRequestCarPool(guestDestinationLocation),
            localAuthDataSource.getToken()
        )
    }

    override suspend fun registerCarPool(
        request: registerCarPoolRequest,
    ): Result<Unit> {
        return dataSource.registerCarPool(
            request,
            localAuthDataSource.getToken()
        )
    }

    override suspend fun rejectCarPoolRequest(
        guestId: Long,
    ): Result<Unit> {
        return dataSource.rejectCarPoolRequest(
            guestId,
            localAuthDataSource.getToken()
        )
    }

    override suspend fun acceptCarPoolRequest(
        guestId: Long,
        guestDestination: String,
    ): Result<AcceptCarPoolResponse> {
        return dataSource.acceptCarPoolRequest(
            guestId,
            guestDestination,
            localAuthDataSource.getToken()
        )
    }

}