package org.hoshi.desktoptools.page

import androidx.compose.runtime.Composable
import hoshidesktoptools.composeapp.generated.resources.Res
import hoshidesktoptools.composeapp.generated.resources.ic_calendar
import hoshidesktoptools.composeapp.generated.resources.ic_home
import hoshidesktoptools.composeapp.generated.resources.ic_time
import hoshidesktoptools.composeapp.generated.resources.ic_translate
import org.jetbrains.compose.resources.DrawableResource

enum class Page(val title: String, val icon: DrawableResource, val content: @Composable () -> Unit) {
    Home("Hoshi 桌面工具", Res.drawable.ic_home, { HomePage() }),
    WhatWeek("第几周", Res.drawable.ic_calendar, { WhatWeekPage() }),
    RadixConversion("进制转换", Res.drawable.ic_translate, { RadixConversionPage() }),
    TimestampConversion("时间戳转换", Res.drawable.ic_time, { TimestampConversionPage() }),
}