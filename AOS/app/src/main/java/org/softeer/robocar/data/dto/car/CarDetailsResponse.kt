package org.softeer.robocar.data.dto.car

import kotlinx.serialization.Serializable

@Serializable
data class CarDetailsResponse(
    val message: String,
    val data: CarData
)

@Serializable
data class CarData(
    val id: Long,
    val garage: GarageData,
    val state: String,
    val carType: String,
    val seatTemperature: Int,
    val ventilationLevel: Int,
    val airConditionerTemperature: Int,
    val doorLock: Boolean,
    val carName: String,
    val carImage: String,
    val carNumber: String
)

@Serializable
data class GarageData(
    val id: Long,
    val latitude: Double,
    val longitude: Double
)