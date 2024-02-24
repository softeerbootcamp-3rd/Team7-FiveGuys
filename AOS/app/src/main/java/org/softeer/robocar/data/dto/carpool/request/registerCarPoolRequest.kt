package org.softeer.robocar.data.dto.carpool.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class registerCarPoolRequest(
    val departAddress: String,
    val destAddress: String,
    @SerialName("maleCount")
    val countOfMen: Int,
    @SerialName("femaleCount")
    val countOfWomen: Int,
    val carType: String
)