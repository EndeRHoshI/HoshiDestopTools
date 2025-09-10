package org.hoshi.desktoptools.utils

import java.util.Calendar

object TimeUtils {

    // const val MILLIS = 1L
    const val SEC = 1000L
    const val MIN = SEC * 60
    const val HOUR = MIN * 60
    const val DAY = HOUR * 24

    fun getCommonCalendar(currentTime: Long): Calendar = Calendar.getInstance().apply {
        firstDayOfWeek = Calendar.MONDAY
        timeInMillis = currentTime
    }

}