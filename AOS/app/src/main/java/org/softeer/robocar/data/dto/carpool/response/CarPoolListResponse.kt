package org.softeer.robocar.data.dto.carpool.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CarPoolListResponse(
    @SerialName("price")
    val originalCharge: Int,
    val list: List<CarPoolInformation>
)

@Serializable
data class CarPoolInformation(
    val hostId: Long,
    val hostNickname: String,
    val departLongitude: Double,
    val departLatitude: Double,
    val hostDestLongitude: Double,
    val hostDestLatitude: Double,
    val hostDepartAddress: String,
    val hostDestAddress: String,
    val maleCount: Int,
    val femaleCount: Int,
    val estimatedTime: Int,
    val estimatedPrice: Int,
)