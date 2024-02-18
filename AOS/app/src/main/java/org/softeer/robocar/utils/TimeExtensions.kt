package org.softeer.robocar.utils

fun convertMinutesToHoursAndMinutes(totalMinutes: UInt): Pair<UInt, UInt> {
    val hours = totalMinutes / 60u
    val minutes = totalMinutes % 60u
    return Pair(hours, minutes)
}

fun formatDurationText(hours: UInt, minutes: UInt): String {
    return when {
        minutes == 0U -> "$hours$HOURS_TEXT $EXPECTED_TIME_TEXT"
        hours == 0U -> "$minutes$MINUTES_TEXT $EXPECTED_TIME_TEXT"
        else -> "$hours$HOURS_TEXT $minutes$MINUTES_TEXT $EXPECTED_TIME_TEXT"
    }
}

const val HOURS_TEXT = "시간"
const val MINUTES_TEXT = "분"
const val EXPECTED_TIME_TEXT = "소요예정"