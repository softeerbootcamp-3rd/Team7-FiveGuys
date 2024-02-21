package org.softeer.robocar.data.dto.user.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    @SerialName("loginId")
    val userId: String,
    val password: String,
    val nickname: String,
)