package org.hoshi.destoptools

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.hoshi.destoptools.page.RadixConversionPage
import org.hoshi.destoptools.page.Router
import org.hoshi.destoptools.page.TimestampConversionPage
import org.hoshi.destoptools.page.WhatWeekPage
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    var page by remember { mutableStateOf(Router.HOME) } // 当前页面
    fun routeToHome() {
        page = Router.HOME
    } // 返回主页

    when (page) {
        Router.HOME -> {
            val padding = 16.dp
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Row {
                    Button(
                        shape = RoundedCornerShape(4.dp),
                        onClick = { page = Router.WHAT_WEEK },
                        modifier = Modifier.padding(padding)
                    ) {
                        Text("第几周")
                    }
                    Button(
                        shape = RoundedCornerShape(4.dp),
                        onClick = { page = Router.RADIX_CONVERSION },
                        modifier = Modifier.padding(padding)
                    ) {
                        Text("进制转换")
                    }
                    Button(
                        shape = RoundedCornerShape(4.dp),
                        onClick = { page = Router.TIMESTAMP_CONVERSION },
                        modifier = Modifier.padding(padding)
                    ) {
                        Text("时间戳转换")
                    }
                }
            }
        }

        Router.WHAT_WEEK -> WhatWeekPage { routeToHome() }
        Router.RADIX_CONVERSION -> RadixConversionPage { routeToHome() }
        Router.TIMESTAMP_CONVERSION -> TimestampConversionPage { routeToHome() }
    }
}