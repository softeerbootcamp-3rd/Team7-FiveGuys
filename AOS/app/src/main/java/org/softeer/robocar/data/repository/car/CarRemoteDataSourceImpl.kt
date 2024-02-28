package org.softeer.robocar.data.repository.car

import org.softeer.robocar.data.dto.car.CarData
import org.softeer.robocar.data.service.car.CarService
import javax.inject.Inject

class CarRemoteDataSourceImpl @Inject constructor(
    private val carService: CarService
): CarRemoteDataSource {

    override suspend fun getCarDetails(
        carId: Long,
        token: String,
    ): Result<CarData> {
        return runCatching {
            carService.getCarDetails(carId,token)
        }
    }
}
