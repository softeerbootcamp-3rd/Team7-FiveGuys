package org.softeer.robocar.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class TaxiType(val size: String) : Parcelable {
    COMPACT_TAXI("SMALL"),
    MID_SIZE_TAXI("MEDIUM"),
}