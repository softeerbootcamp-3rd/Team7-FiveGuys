package org.softeer.robocar.data.dto.login.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("id")
    val userId: Long,
    val nickname: String,
    val token: String,
)