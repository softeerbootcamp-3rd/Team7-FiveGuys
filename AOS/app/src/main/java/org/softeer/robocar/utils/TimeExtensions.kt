package org.softeer.robocar.utils

fun convertMinutesToHoursAndMinutes(totalMinutes: Int): Pair<Int, Int> {
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60
    return Pair(hours, minutes)
}

fun formatDurationText(hours: Int, minutes: Int): String {
    return when {
        minutes == 0 -> "$hours$HOURS_TEXT $EXPECTED_TIME_TEXT"
        hours == 0 -> "$minutes$MINUTES_TEXT $EXPECTED_TIME_TEXT"
        else -> "$hours$HOURS_TEXT $minutes$MINUTES_TEXT $EXPECTED_TIME_TEXT"
    }
}

private const val HOURS_TEXT = "시간"
private const val MINUTES_TEXT = "분"
private const val EXPECTED_TIME_TEXT = "소요예정"