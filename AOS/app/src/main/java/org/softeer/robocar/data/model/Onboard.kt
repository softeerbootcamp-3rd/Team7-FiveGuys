package org.softeer.robocar.data.model

data class Onboard(
    val departureAddress: String,
    val hostDestAddress: String,
    val guestDestAddress: String,
    val hostId: Long,
    val guestId: Long
)