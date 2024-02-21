package org.softeer.robocar.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class CarPoolType : Parcelable {
    ALONE,
    RECRUIT,
    JOIN,
}