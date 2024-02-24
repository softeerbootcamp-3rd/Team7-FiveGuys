package org.softeer.robocar.data.dto.carpool.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AcceptCarPoolResponse(
    @SerialName("carPoolId")
    val inOperationId: Long
)
