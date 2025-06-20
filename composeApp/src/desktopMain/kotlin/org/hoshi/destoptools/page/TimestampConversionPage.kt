package org.hoshi.destoptools.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.hoshi.destoptools.data.TimeUnit
import org.hoshi.destoptools.extentions.matchTrue
import org.hoshi.destoptools.utils.rememberTicker
import org.hoshi.destoptools.widget.DropdownMenu
import java.text.SimpleDateFormat

@Composable
fun TimestampConversionPage(backAction: () -> Unit) {
    val currentTime by rememberTicker().tickerFlow.collectAsState() // 单位毫秒，内部使用一个 Job 来定时刷新，且生命周期跟随布局
    val timeUnit = remember { mutableStateOf(TimeUnit.MILLIS) }
    var sourceTimestamp by remember { mutableStateOf(currentTime) } // 直接从 flow 里面取一下当前时间

    Column {
        IconButton(
            { backAction.invoke() }
        ) {
            Text("返回")
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.safeContentPadding()
            ) {
                Text("当前 timestamp 是 ${currentTime / 1000}，${getCurrentTimeStr(currentTime)}")
                Row(
                    verticalAlignment = Alignment.CenterVertically
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
            }
        }
    }
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