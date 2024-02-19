package org.softeer.robocar.data.model

data class CarPool(
    val carPoolId: Long,
    val duration: Int,
    val startLocation: String,
    val destinationLocation: String,
    val nickname: String,
    val expectedCharge: Int,
    val countOfMen: Int,
    val countOfWomen: Int,
)
