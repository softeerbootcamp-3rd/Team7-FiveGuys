package org.softeer.robocar.data.dto.carpool.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestCarPoolRequest(
    @SerialName("hostId")
    val carPoolId: Long,
    val nickname: String,
    @SerialName("maleCount")
    val countOfMale: Int,
    @SerialName("femaleCount")
    val countOfFemale: Int
)