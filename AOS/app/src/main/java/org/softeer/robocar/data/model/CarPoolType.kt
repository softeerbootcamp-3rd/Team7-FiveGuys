package org.softeer.robocar.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class CarPoolType(val type: String) : Parcelable {
    ALONE("ALONE"),
    RECRUIT("RECRUIT"),
    JOIN("JOIN");

    companion object {
        fun getType(_type: String): CarPoolType {
            for (enum in values()) {
                if (enum.type.equals(_type, ignoreCase = true)) {
                    return enum
                }
            }
            return ALONE
        }
    }
}
