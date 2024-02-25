package org.softeer.robocar.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class TaxiType(val size: String) : Parcelable {
    COMPACT_TAXI("SMALL"),
    MID_SIZE_TAXI("MEDIUM");

    companion object {
        fun getSize(_type: String): TaxiType{
            TaxiType.entries.map {
                if(_type.equals(it))
                    return it
            }
            return TaxiType.COMPACT_TAXI
        }
    }
}