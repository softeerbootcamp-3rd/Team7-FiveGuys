package org.softeer.robocar.data.dto.carpool.request

import kotlinx.serialization.Serializable

@Serializable
data class RejectCarPoolRequest(
    val guestId: Long,
)
