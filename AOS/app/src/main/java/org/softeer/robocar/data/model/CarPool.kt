package org.softeer.robocar.data.model

data class CarPool(
    val carPoolId: Long,
    val duration: UInt,
    val startLocation: String,
    val destinationLocation: String,
    val nickname: String,
    val expectedCharge: UInt,
    val countOfMen: UInt,
    val countOfWomen: UInt,
)
