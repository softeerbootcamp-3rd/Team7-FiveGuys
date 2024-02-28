package org.softeer.robocar.data.mapper

import org.softeer.robocar.data.dto.car.CarData
import org.softeer.robocar.data.model.CarDetails

fun CarData.toCarDetails(): CarDetails {
    return CarDetails(
        id = id,
        state = state,
        carType = carType,
        seatTemperature = seatTemperature,
        ventilationLevel = ventilationLevel,
        airConditionerTemperature = airConditionerTemperature,
        doorLock = doorLock,
        carName = carName,
        carImage = carImage,
        carNumber = carNumber
    )
}