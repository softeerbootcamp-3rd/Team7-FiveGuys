package org.softeer.robocar.data.dto.login.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val loginId: String,
    val password: String,
)