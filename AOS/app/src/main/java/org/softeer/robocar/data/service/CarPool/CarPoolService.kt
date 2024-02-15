package org.softeer.robocar.data.service.CarPool

import org.softeer.robocar.data.dto.carpool.request.CarPoolListRequest
import org.softeer.robocar.data.dto.carpool.response.CarPoolListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface CarPoolService {

    @GET("operations/carpools")
    suspend fun getCarPoolList(@Body carPoolListRequest: CarPoolListRequest): Response<CarPoolListResponse>

}