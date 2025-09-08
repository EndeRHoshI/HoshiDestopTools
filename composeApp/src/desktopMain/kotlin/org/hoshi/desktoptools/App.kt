package org.hoshi.desktoptools

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hoshidesktoptools.composeapp.generated.resources.*
import org.hoshi.desktoptools.page.HomePage
import org.hoshi.desktoptools.page.RadixConversionPage
import org.hoshi.desktoptools.page.Router
import org.hoshi.desktoptools.page.TimestampConversionPage
import org.hoshi.desktoptools.page.WhatWeekPage
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    var page by remember { mutableStateOf(Router.HOME) } // 当前页面

    Row {
        Column(
            modifier = Modifier.fillMaxHeight().width(80.dp).background(Color.Gray)
        ) {
            Spacer(Modifier.height(20.dp))
            PageIcon({ page = Router.HOME }, Res.drawable.ic_home)
            PageIcon({ page = Router.WHAT_WEEK }, Res.drawable.ic_calendar)
            PageIcon({ page = Router.RADIX_CONVERSION }, Res.drawable.ic_translate)
            PageIcon({ page = Router.TIMESTAMP_CONVERSION }, Res.drawable.ic_time)
        }

        when (page) {
            Router.HOME -> HomePage()
            Router.WHAT_WEEK -> WhatWeekPage()
            Router.RADIX_CONVERSION -> RadixConversionPage()
            Router.TIMESTAMP_CONVERSION -> TimestampConversionPage()
        }
    }

}

/**
 * 切换页面的 Icon
 * @param onClick 点击事件
 * @param iconDrawableRes 图标资源
 */
@Composable
fun PageIcon(onClick: () -> Unit, iconDrawableRes: DrawableResource) {
    Box(
        Modifier.fillMaxWidth().clickable { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(iconDrawableRes),
            "",
            modifier = Modifier.padding(30.dp, 18.dp)
        )
    }
}