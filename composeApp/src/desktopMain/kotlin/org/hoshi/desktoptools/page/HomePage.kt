package org.hoshi.desktoptools.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.hoshi.desktoptools.res.ColorRes

/**
 * 主页
 */
@Composable
fun HomePage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 22.dp, start = 40.dp),
    ) {
        Column {
            Text(
                "当前版本：${System.getProperty("jpackage.app-version")?.let { "v$it" } ?: "正式打包后才会显示"}",
                color = ColorRes.textPrimary
            )
        }
    }
}
