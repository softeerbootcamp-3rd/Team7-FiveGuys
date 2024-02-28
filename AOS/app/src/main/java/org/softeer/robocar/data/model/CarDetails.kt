package org.softeer.robocar.data.model

data class CarDetails(
    val id: Long,
    val state: String,
    val carType: String,
    val seatTemperature: Int,
    val ventilationLevel: Int,
    val airConditionerTemperature: Int,
    val doorLock: Boolean,
    val carName: String,
    val carImage: String,
    val carNumber: String,
    val garage: GarageDetails // 차고지 정보 추가
)

data class GarageDetails(
    val id: Long,
    val latitude: Double,
    val longitude: Double
)
