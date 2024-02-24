package org.softeer.robocar.data.dto.user.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckAvailableUserIdResponse(
    @SerialName("available")
    val isAvailable: Boolean
)