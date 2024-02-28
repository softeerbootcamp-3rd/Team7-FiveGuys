package org.softeer.robocar.data.mapper

import org.softeer.robocar.data.dto.car.CarData
import org.softeer.robocar.data.dto.car.GarageData
import org.softeer.robocar.data.model.CarDetails
import org.softeer.robocar.data.model.GarageDetails

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
        carNumber = carNumber,
        garage = this.garage.toGarageDetails() // 차고지 정보 변환
    )
}

fun GarageData.toGarageDetails(): GarageDetails {
    return GarageDetails(
        id = id,
        latitude = latitude,
        longitude = longitude
    )
}
