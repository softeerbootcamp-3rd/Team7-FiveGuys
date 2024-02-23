package org.softeer.robocar.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaceItem (
    val place_name: String,
    val road_address_name: String, // 전체 도로명 주소
): Parcelable