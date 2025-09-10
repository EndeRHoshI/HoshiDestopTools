package org.hoshi.desktoptools.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.hoshi.desktoptools.utils.TimeUtils
import org.hoshi.desktoptools.utils.rememberTicker
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WhatWeekPage() {
    val ticker = rememberTicker()
    val currentTime by ticker.tickerFlow.collectAsState()
    val untilFridayStr by mutableStateOf("距离周五下班还有 ${getTheTimeUntilFriday(currentTime)}")
    val clipboard = LocalClipboardManager.current // 新版本不会用，先用着被弃用的版本先
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
                Spacer(Modifier.height(10.dp))
                Text(getCurrentYearDay(currentTime))
                Spacer(Modifier.height(10.dp))
                LinearProgressIndicator(
                    progress = { getCurrentDay(currentTime).toFloat() / 365 },
                    modifier = Modifier.width(300.dp),
                    gapSize = 0.dp,
                    drawStopIndicator = {}
                )
                Spacer(Modifier.height(10.dp))
                Text(getCurrentMonthWeek(currentTime))
                Text(getCurrentYearWeekDay(currentTime))
                Spacer(Modifier.height(40.dp))
                Text(
                    untilFridayStr,
                    Modifier.clickable {
                        MainScope().launch {
                            clipboard.setText(AnnotatedString(untilFridayStr))
                        }
                    }
                )
            }
        }
    }
}

private fun getCurrentTimeStr(currentTime: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return sdf.format(currentTime)
}

private fun getCurrentDay(currentTime: Long): Int {
    val calendar = TimeUtils.getCommonCalendar(currentTime)
    return calendar.get(Calendar.DAY_OF_YEAR) // 获取当年第几天
}

private fun getCurrentYearDay(currentTime: Long): String {
    val calendar = TimeUtils.getCommonCalendar(currentTime)
    val year = calendar.get(Calendar.YEAR) // 获取年份
    return "$year 年的第 ${getCurrentDay(currentTime)} 天"
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
        day--
    }
    return "$year 年的第 $week 周第 $day 天"
}

private fun getTheTimeUntilFriday(currentTime: Long): String {
    // 首先取得本周五 18:30 的时间戳
    val calendar = TimeUtils.getCommonCalendar(currentTime)
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY) // 设置到周五
    calendar.set(Calendar.HOUR_OF_DAY, 18) // 设置到 18 点
    calendar.set(Calendar.MINUTE, 30) // 设置到 30 分
    calendar.set(Calendar.SECOND, 0) // 设置到 0 秒
    val targetTimeStamp = calendar.time.time

    var diff = targetTimeStamp - currentTime
    // println("diff = $diff，targetTimeStamp = $targetTimeStamp，currentTime = $currentTime")
    if (diff <= 0) {
        return "周末快乐"
    } else {
        val day = diff / TimeUtils.DAY
        diff = diff % TimeUtils.DAY
        val hour = diff / TimeUtils.HOUR
        diff = diff % TimeUtils.HOUR
        val min = diff / TimeUtils.MIN
        diff = diff % TimeUtils.MIN
        val sec = diff / TimeUtils.SEC
        return "$day 天 $hour 时 $min 分 $sec 秒"
    }
}
