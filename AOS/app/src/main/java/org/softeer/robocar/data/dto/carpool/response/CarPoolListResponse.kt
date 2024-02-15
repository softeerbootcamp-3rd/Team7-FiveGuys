package org.softeer.robocar.data.dto.carpool.response

data class CarPoolListResponse(
    val hostId: Long,
    val nickname: String,
    val departAltitude: Double,
    val departLatitude: Double,
    val hostDestAltitude: Double,
    val hostDestLatitude: Double,
    val hostDepartAddress: String,
    val hostDestAddress: String,
    val maleCount: Int,
    val femaleCount: Int,
    val estimatedTime: String,
    val estimatedPrice: String,
)