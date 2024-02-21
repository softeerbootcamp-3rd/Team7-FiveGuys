package org.softeer.robocar.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class TaxiType : Parcelable {
    COMPACT_TAXI,
    MID_SIZE_TAXI,
}