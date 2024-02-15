package org.softeer.robocar.data.repository.CarPool

import org.softeer.robocar.data.dto.carpool.request.CarPoolListRequest
import org.softeer.robocar.data.dto.carpool.response.CarPoolListResponse
import org.softeer.robocar.data.service.CarPool.CarPoolService
import retrofit2.Response

class CarPoolRemoteDataSourceImpl(private val carPoolService: CarPoolService) : CarPoolRemoteDataSource {

    override suspend fun getCarPoolList(
        guestStartLocation: String,
        guestDestinationLocation: String
    ): Response<CarPoolListResponse> {

        val requestBody = CarPoolListRequest(
            guestDepartAddress = guestStartLocation,
            guestDestAddress = guestDestinationLocation
        )

        return carPoolService.getCarPoolList(requestBody)
    }
}