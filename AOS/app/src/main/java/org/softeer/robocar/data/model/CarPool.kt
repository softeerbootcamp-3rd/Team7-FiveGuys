package org.softeer.robocar.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarPool(
    val carPoolId: Long,
    val duration: Int,
    val startLocation: String,
    val destinationLocation: String,
    val nickname: String,
    val expectedCharge: Int,
    val countOfMen: Int,
    val countOfWomen: Int,
) : Parcelable
