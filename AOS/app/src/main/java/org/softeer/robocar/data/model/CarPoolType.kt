package org.softeer.robocar.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class CarPoolType(val type: String) : Parcelable {
    ALONE("ALONE"),
    RECRUIT("RECRUIT"),
    JOIN("JOIN");

    companion object {
        fun getType(_type: String): CarPoolType{
            CarPoolType.entries.map {
                if(_type.equals(it.type))
                    return it
            }
            return ALONE
        }
    }
}
