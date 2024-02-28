package org.softeer.robocar.data.repository.car

import org.softeer.robocar.data.dto.car.CarData
import org.softeer.robocar.data.dto.car.CarDetailsResponse

interface CarRemoteDataSource {
    suspend fun getCarDetails(
        carId: Long,
        token: String,
        ): Result<CarData>
}