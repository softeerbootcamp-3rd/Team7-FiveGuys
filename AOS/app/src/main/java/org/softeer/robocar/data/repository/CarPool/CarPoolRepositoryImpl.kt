package org.softeer.robocar.data.repository.CarPool

import org.softeer.robocar.data.dto.carpool.request.registerCarPoolRequest
import org.softeer.robocar.data.dto.carpool.response.AcceptCarPoolResponse
import org.softeer.robocar.data.mapper.toRequestCarPool
import org.softeer.robocar.data.model.CarPool
import org.softeer.robocar.data.model.CarPools
import javax.inject.Inject

class CarPoolRepositoryImpl @Inject constructor(
    private val dataSource: CarPoolRemoteDataSource,
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
        )
    }

    override suspend fun requestCarPool(
        carPool: CarPool,
        guestDestinationLocation: String
    ): Result<Unit> {
        return dataSource.requestCarPool(
            carPool.toRequestCarPool(guestDestinationLocation)
        )
    }

    override suspend fun registerCarPool(
        request: registerCarPoolRequest
    ): Result<Unit> {
        return dataSource.registerCarPool(
            request
        )
    }

    override suspend fun rejectCarPoolRequest(
        guestId: Long
    ): Result<Unit> {
        return dataSource.rejectCarPoolRequest(
            guestId
        )
    }

    override suspend fun acceptCarPoolRequest(
        guestId: Long,
        guestDestination: String
    ): Result<AcceptCarPoolResponse> {
        return dataSource.acceptCarPoolRequest(
            guestId,
            guestDestination
        )
    }

}