package org.hoshi.desktoptools.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.hoshi.desktoptools.utils.TimeUtils
import org.hoshi.desktoptools.utils.rememberTicker
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WhatWeekPage() {
    val ticker = rememberTicker()
    val currentTime by ticker.tickerFlow.collectAsState()

    Column {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 22.dp),
            contentAlignment = Alignment.TopCenter
        ) {

            Column(
                modifier = Modifier
                    .safeContentPadding()
            ) {
                Text("当前是 ${getCurrentTimeStr(currentTime)}")
                Text(getCurrentYearDay(currentTime))
                Text(getCurrentMonthWeek(currentTime))
                Text(getCurrentYearWeekDay(currentTime))
            }
        }
    }
}

private fun getCurrentTimeStr(currentTime: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd-HH:mm:ss")
    return sdf.format(currentTime)
}

private fun getCurrentYearDay(currentTime: Long): String {
    val calendar = TimeUtils.getCommonCalendar(currentTime)
    val year = calendar.get(Calendar.YEAR) // 获取年份
    val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR) // 获取当年第几天
    return "$year 年的第 $dayOfYear 天"
}

private fun getCurrentMonthWeek(currentTime: Long): String {
    val calendar = TimeUtils.getCommonCalendar(currentTime)
    val month = calendar.get(Calendar.MONTH) + 1 // 获取月份（注意：Calendar 月份从 0 开始，需要 + 1）
    val week = calendar.get(Calendar.WEEK_OF_MONTH)// 获取当月第几周
    return "$month 月的第 $week 周"
}

private fun getCurrentYearWeekDay(currentTime: Long): String {
    val calendar = TimeUtils.getCommonCalendar(currentTime)
    val year = calendar.get(Calendar.YEAR) // 获取年份
    val week = calendar.get(Calendar.WEEK_OF_YEAR) // 获取当年第几周
    var day = calendar.get(Calendar.DAY_OF_WEEK) // 获取星期几
    // 转换成当周第几天，1 是周日，换成第 7 天，其余星期几直接减去 1 即可
    if (day == 1) {
        day = 7
    } else {
        day --
    }
    return "$year 年的第 $week 周第 $day 天"
}
