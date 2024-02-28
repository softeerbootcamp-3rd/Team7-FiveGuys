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
    val carNumber: String
)
