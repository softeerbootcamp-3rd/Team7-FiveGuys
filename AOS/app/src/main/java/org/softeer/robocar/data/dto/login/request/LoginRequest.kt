package org.softeer.robocar.data.dto.login.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("loginId")
    val userId: String,
    val password: String,
)