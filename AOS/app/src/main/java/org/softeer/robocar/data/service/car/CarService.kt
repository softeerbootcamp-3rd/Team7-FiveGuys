package org.softeer.robocar.data.service.car

import org.softeer.robocar.data.dto.car.CarData
import org.softeer.robocar.data.dto.car.CarDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CarService {
    @GET("cars/{carId}")
    suspend fun getCarDetails(
        @Path("carId") carId: Long,
        @Header("Authorization") token: String,
    ): CarData
}