package org.hoshi.desktoptools

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import hoshidesktoptools.composeapp.generated.resources.Res
import hoshidesktoptools.composeapp.generated.resources.icon
import org.hoshi.desktoptools.res.IconRes
import org.hoshi.desktoptools.res.icons.Github
import org.hoshi.desktoptools.extentions.openBrowse
import org.hoshi.desktoptools.widget.ActionIconButton
import org.hoshi.desktoptools.widget.WindowTopBar
import org.hoshi.desktoptools.window.AppWindow
import org.jetbrains.compose.resources.painterResource

fun main() = application {
    AppWindow(
        onCloseRequest = ::exitApplication,
        title = "HoshiDesktopTools",
        icon = painterResource(Res.drawable.icon), // 注意这个是窗口的图标，和应用图标不同，完全两码事
        undecorated = true, // 是否隐藏标题栏
        resizable = false, // 窗口是否可以调节大小
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                WindowDraggableArea { // 顶部可拖拽操作栏
                    WindowTopBar(
                        onCloseRequest = ::exitApplication,
                        title = {
                            androidx.compose.material.Text(
                                text = window.title,
                                style = MaterialTheme.typography.h6,
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                            )
                        }
                    ) {
                        ActionIconButton(
                            onClick = { "https://github.com/EndeRHoshI/HoshiDestopTools".openBrowse() }
                        ) {
                            Icon(
                                imageVector = IconRes.Github,
                                contentDescription = "github",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        ) { innerPadding -> // 这个 innerPadding 是用来让 content 不和标题栏重叠的
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {
                App()
            }
        }
    }
}