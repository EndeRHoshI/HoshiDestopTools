package org.hoshi.desktoptools

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import hoshidesktoptools.composeapp.generated.resources.Res
import hoshidesktoptools.composeapp.generated.resources.icon
import org.jetbrains.compose.resources.painterResource

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "HoshiDesktopTools",
        icon = painterResource(Res.drawable.icon), // 注意这个是窗口的图标，和应用图标不同，完全两码事
        undecorated = true, // 是否隐藏标题栏
        resizable = false, // 窗口是否可以调节大小
    ) {
        Column {
            WindowDraggableArea(modifier = Modifier.fillMaxWidth().height(20.dp).background(Color.Gray)) {
                Row {
                    Text("关闭", modifier = Modifier.clickable { exitApplication() })
                    Text("最小化", modifier = Modifier.clickable {  })
                }
            }
            App()
        }
    }

}