package org.hoshi.desktoptools.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.hoshi.desktoptools.data.TimeUnit
import org.hoshi.desktoptools.extentions.matchTrue
import org.hoshi.desktoptools.res.ColorRes
import org.hoshi.desktoptools.utils.TimeUtils
import org.hoshi.desktoptools.utils.rememberTicker
import org.hoshi.desktoptools.widget.DropdownMenu
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TimestampConversionPage() {
    val currentTime by rememberTicker().tickerFlow.collectAsState() // 单位毫秒，内部使用一个 Job 来定时刷新，且生命周期跟随布局
    val timeUnit = remember { mutableStateOf(TimeUnit.MILLIS) }
    var sourceTimestamp by remember { mutableStateOf(currentTime) } // 直接从 flow 里面取一下当前时间
    val year = remember {
        mutableStateOf(
            TimeUtils.getCommonCalendar(sourceTimestamp).get(Calendar.YEAR).toString()
        )
    }
    val month = remember {
        mutableStateOf(
            (TimeUtils.getCommonCalendar(sourceTimestamp).get(Calendar.MONTH) + 1).toString()
        )
    }
    val day = remember {
        mutableStateOf(
            TimeUtils.getCommonCalendar(sourceTimestamp).get(Calendar.DAY_OF_MONTH).toString()
        )
    }
    val hour = remember {
        mutableStateOf(
            TimeUtils.getCommonCalendar(sourceTimestamp).get(Calendar.HOUR_OF_DAY).toString()
        )
    }
    val minute = remember {
        mutableStateOf(
            TimeUtils.getCommonCalendar(sourceTimestamp).get(Calendar.MINUTE).toString()
        )
    }
    val second = remember {
        mutableStateOf(
            TimeUtils.getCommonCalendar(sourceTimestamp).get(Calendar.SECOND).toString()
        )
    }
    var targetTimestamp by remember { mutableStateOf("") } // 输入日期转换时间戳的结果

    Column {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 22.dp, start = 60.dp, end = 60.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column {
                Text(
                    "当前 timestamp 是 ${currentTime / 1000}，${getCurrentTimeStr(currentTime)}",
                    modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center),
                    color = ColorRes.textPrimary
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)
                ) {
                    BasicTextField(
                        value = sourceTimestamp.toString(),
                        onValueChange = {
                            sourceTimestamp = it.toLongOrNull() ?: 0L
                        },
                        modifier = Modifier
                            .width(160.dp)
                            .background(Color.White, RoundedCornerShape(4.dp))
                            .height(35.dp),
                        decorationBox = { innerTextField ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp)
                            ) {
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    innerTextField()
                                }
                            }
                        }
                    )
                    DropdownMenu(timeUnit)
                    BasicTextField(
                        value = getFormattedTimeStr(sourceTimestamp.toString(), timeUnit.value),
                        onValueChange = { },
                        modifier = Modifier
                            .width(240.dp)
                            .background(Color.White, RoundedCornerShape(4.dp))
                            .height(35.dp),
                        decorationBox = { innerTextField ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp)
                            ) {
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    innerTextField()
                                }
                            }
                        }
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(0.dp, 120.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center)
                ) {
                    Text("时间", color = ColorRes.textPrimary)
                    DateView(year, "年", 70.dp)
                    DateView(month, "月")
                    DateView(day, "日")
                    DateView(hour, "时")
                    DateView(minute, "分")
                    DateView(second, "秒")
                }
                Box(
                    modifier = Modifier
                        .padding(0.dp, 12.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center),
                ) {
                    Button(
                        modifier = Modifier.padding(10.dp, 0.dp)
                            .defaultMinSize(1.dp, 1.dp)
                            .size(50.dp, 30.dp),
                        contentPadding = PaddingValues(0.dp),
                        shape = RoundedCornerShape(4.dp),
                        onClick = {
                            targetTimestamp = getTimestamp(
                                year.value,
                                month.value,
                                day.value,
                                hour.value,
                                minute.value,
                                second.value
                            )
                        }
                    ) {
                        Text("转换")
                    }

                }

                Box(
                    modifier = Modifier
                        .padding(0.dp, 12.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center),
                ) {
                    BasicTextField(
                        value = targetTimestamp,
                        onValueChange = { },
                        modifier = Modifier
                            .width(160.dp)
                            .background(Color.White, RoundedCornerShape(4.dp))
                            .height(35.dp),
                        decorationBox = { innerTextField ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp)
                            ) {
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    innerTextField()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DateView(dateState: MutableState<String>, unit: String, width: Dp = 50.dp) {
    BasicTextField(
        value = dateState.value,
        onValueChange = { dateState.value = it },
        modifier = Modifier
            .width(width)
            .padding(12.dp, 0.dp, 0.dp, 0.dp)
            .background(Color.White, RoundedCornerShape(4.dp))
            .height(35.dp),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    innerTextField()
                }
            }
        }
    )
    Text(
        unit,
        modifier = Modifier.padding(12.dp, 0.dp, 0.dp, 0.dp),
        color = ColorRes.textPrimary
    )
}

private fun getCurrentTimeStr(currentTime: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd-HH:mm:ss")
    return sdf.format(currentTime)
}

private fun getFormattedTimeStr(currentTimeStr: String, unit: TimeUnit = TimeUnit.MILLIS): String {
    val currentTime = (currentTimeStr.toLongOrNull() ?: 0) * (unit == TimeUnit.SEC).matchTrue(1000, 1)
    val sdf = SimpleDateFormat("yyyy-MM-dd-HH:mm:ss")
    return sdf.format(currentTime)
}

private fun getTimestamp(
    year: String,
    month: String,
    day: String,
    hour: String,
    minute: String,
    second: String
): String {
    val yearInt = year.toIntOrNull()
    val monthInt = (month.toIntOrNull() ?: 0) - 1 // 月份要减去 1 才对，比如 1 月份就应该是 0
    val dayInt = day.toIntOrNull()
    val hourInt = hour.toIntOrNull()
    val minuteInt = minute.toIntOrNull()
    val secondInt = second.toIntOrNull()
    if (yearInt == null || yearInt < 0) {
        return "年份输入错误"
    }
    if (monthInt < 0 || monthInt > 12) {
        return "月输入错误"
    }
    if (dayInt == null || dayInt < 0 || dayInt > 31) {
        return "日输入错误"
    }
    if (hourInt == null || hourInt < 0 || hourInt > 23) {
        return "时输入错误"
    }
    if (minuteInt == null || minuteInt < 0 || minuteInt > 59) {
        return "分输入错误"
    }
    if (secondInt == null || secondInt < 0 || secondInt > 59) {
        return "秒输入错误"
    }
    val calendar = Calendar.getInstance().apply {
        set(yearInt, monthInt, dayInt, hourInt, minuteInt, secondInt)
    }
    return calendar.time.time.toString()
}