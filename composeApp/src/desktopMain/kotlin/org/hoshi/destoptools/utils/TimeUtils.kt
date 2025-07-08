package org.hoshi.destoptools.utils

import java.util.Calendar

object TimeUtils {

    fun getCommonCalendar(currentTime: Long) = Calendar.getInstance().apply {
        firstDayOfWeek = Calendar.MONDAY
        timeInMillis = currentTime
    }

}