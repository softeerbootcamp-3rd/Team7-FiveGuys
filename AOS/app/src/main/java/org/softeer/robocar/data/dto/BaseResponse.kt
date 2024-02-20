package org.softeer.robocar.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val message: String,
    val data: T
)