package org.softeer.robocar.utils

fun convertMillsToHoursAndMinutes(totalMills: Int): Pair<Int, Int> {
    val hours = totalMills / 3600000
    val minutes = (totalMills % 3600000)/60000
    return Pair(hours, minutes)
}

fun formatMillsDurationText(hours: Int, minutes: Int): String {
    return when {
        minutes == 0 && hours == 0 -> "1분 이내 도착 예정"
        minutes == 0 -> "$hours$HOURS_TEXT $EXPECTED_TIME_TEXT"
        hours == 0 -> "$minutes$MINUTES_TEXT $EXPECTED_TIME_TEXT"
        else -> "$hours$HOURS_TEXT $minutes$MINUTES_TEXT $EXPECTED_TIME_TEXT"
    }
}

private const val HOURS_TEXT = "시간"
private const val MINUTES_TEXT = "분"
private const val EXPECTED_TIME_TEXT = "소요예정"