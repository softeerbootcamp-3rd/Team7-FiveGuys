package org.softeer.robocar.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaceItem (
    val taxiType: TaxiType,
    val carPoolType: CarPoolType,
    val countMale: String,
    val countFemale: String,
    val departureName: String,
    val departureRoadAddressName: String,
    val destName: String,
    val destRoadAddressName: String,
): Parcelable