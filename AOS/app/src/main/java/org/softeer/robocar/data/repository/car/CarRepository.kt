package org.softeer.robocar.data.repository.car

import org.softeer.robocar.data.model.CarDetails

interface CarRepository {
    suspend fun getCarDetails(
        carId: Long
    ): CarDetails
}