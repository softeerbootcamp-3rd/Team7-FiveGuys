package org.softeer.robocar.data.dto.operation

import kotlinx.serialization.Serializable

@Serializable
data class OnboardResponse(
    val message: String,
    val data: OnboardData
)

@Serializable
data class OnboardData(
    val id: Int,
    val departureTime: String,
    val departureAddress: String,
    val hostDestAddress: String,
    val guestDestAddress: String,
    val estimatedHostArrivalTime: String?,
    val estimatedGuestArrivalTime: String?,
    val carId: Int,
    val hostId: Int,
    val guestId: Int,
    val guestOnBoard: Boolean,
    val hostOnBoard: Boolean
)