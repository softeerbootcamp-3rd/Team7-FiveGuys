package org.softeer.robocar.data.dto.operation

import kotlinx.serialization.Serializable

@Serializable
data class OnboardResponse(
    val message: String,
    val data: OnboardData
)

@Serializable
data class OnboardData(
    val id: Long,
    val departureTime: String,
    val departureAddress: String,
    val hostDestAddress: String,
    val guestDestAddress: String,
    val estimatedHostArrivalTime: String?,
    val estimatedGuestArrivalTime: String?,
    val carId: Long,
    val hostId: Long,
    val guestId: Long,
    val guestOnBoard: Boolean,
    val hostOnBoard: Boolean
)