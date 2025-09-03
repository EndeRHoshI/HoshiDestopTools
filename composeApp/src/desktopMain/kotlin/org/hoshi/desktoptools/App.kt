package org.hoshi.desktoptools

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hoshidesktoptools.composeapp.generated.resources.Res
import hoshidesktoptools.composeapp.generated.resources.ic_calendar
import hoshidesktoptools.composeapp.generated.resources.ic_home
import hoshidesktoptools.composeapp.generated.resources.ic_time
import hoshidesktoptools.composeapp.generated.resources.ic_translate
import org.hoshi.desktoptools.page.RadixConversionPage
import org.hoshi.desktoptools.page.Router
import org.hoshi.desktoptools.page.TimestampConversionPage
import org.hoshi.desktoptools.page.WhatWeekPage
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
            val boxModifier = Modifier.fillMaxWidth()
            val iconModifier = Modifier.padding(30.dp, 18.dp)
            Box(
                boxModifier.clickable { page = Router.HOME },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_home),
                    "home",
                    modifier = iconModifier
                )
            }
            Box(
                boxModifier.clickable { page = Router.WHAT_WEEK },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_calendar),
                    "",
                    modifier = iconModifier
                )
            }
            Box(
                boxModifier.clickable { page = Router.RADIX_CONVERSION },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_time),
                    "",
                    modifier = iconModifier
                )
            }
            Box(
                boxModifier.clickable { page = Router.TIMESTAMP_CONVERSION },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_translate),
                    "",
                    modifier = iconModifier
                )
            }
        }

        when (page) {
            Router.HOME -> Box(modifier = Modifier.fillMaxSize())
            Router.WHAT_WEEK -> WhatWeekPage()
            Router.RADIX_CONVERSION -> RadixConversionPage()
            Router.TIMESTAMP_CONVERSION -> TimestampConversionPage()
        }
    }

}