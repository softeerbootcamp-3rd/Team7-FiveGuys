package org.softeer.robocar.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaceItem (
    val taxiType: String,
    val carPoolType: String,
    val countMale: String,
    val countFemale: String,
    val departureName: String,
    val departureRoadAddressName: String,
    val destName: String,
    val destRoadAddressName: String,
): Parcelable