package org.softeer.robocar.data.dto.carpool.response

import kotlinx.serialization.Serializable

@Serializable
data class AcceptCarPoolResponse(
    val inOperationId: Long
)
