package org.softeer.robocar.data.repository.CarPool

import org.softeer.robocar.data.dto.carpool.response.CarPoolListResponse
import retrofit2.Response

interface CarPoolRemoteDataSource {

    suspend fun getCarPoolList(
        guestStartLocation: String,
        guestDestinationLocation: String,
    ): Response<CarPoolListResponse>

}