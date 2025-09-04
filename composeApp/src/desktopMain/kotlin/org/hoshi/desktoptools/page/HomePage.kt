package org.hoshi.desktoptools.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.hoshi.desktoptools.extentions.openBrowse

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
            Text("Hoshi 桌面工具", fontSize = 22.sp)
            Spacer(modifier = Modifier.height(20.dp))
            Text("当前版本：${System.getProperty("jpackage.app-version")?.let { "v$it" } ?: "正式打包后才会显示"}")
            Spacer(modifier = Modifier.height(20.dp))
            Text("Github 源码", color = Color.Blue, modifier = Modifier.clickable { "https://github.com/EndeRHoshI/HoshiDestopTools".openBrowse() })
        }
    }
}
