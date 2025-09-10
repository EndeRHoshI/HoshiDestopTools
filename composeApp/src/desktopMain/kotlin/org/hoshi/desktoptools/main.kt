package org.hoshi.desktoptools

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import hoshidesktoptools.composeapp.generated.resources.Res
import hoshidesktoptools.composeapp.generated.resources.icon
import org.hoshi.desktoptools.extentions.openBrowse
import org.hoshi.desktoptools.res.ColorRes
import org.hoshi.desktoptools.res.IconRes
import org.hoshi.desktoptools.res.icons.Blog
import org.hoshi.desktoptools.res.icons.Github
import org.hoshi.desktoptools.widget.ActionIconButton
import org.hoshi.desktoptools.widget.WindowTopBar
import org.hoshi.desktoptools.window.AppWindow
import org.jetbrains.compose.resources.painterResource

fun main() = application {
    AppWindow(
        onCloseRequest = ::exitApplication,
        title = "",
        state = rememberWindowState(
            width = 1080.dp, // 宽
            height = 720.dp, // 高
            position = WindowPosition(Alignment.Center), // 窗口位置
        ),
        icon = painterResource(Res.drawable.icon), // 注意这个是窗口的图标，和应用图标不同，完全两码事
        undecorated = true, // 是否隐藏标题栏，隐藏标题栏后，圆角也会消失掉
        resizable = false, // 窗口是否可以调节大小
    ) {
        AppContentWrapper {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    WindowDraggableArea { // 可拖拽操作区域
                        WindowTopBar(
                            onCloseRequest = ::exitApplication,
                        ) {
                            ActionIconButton(
                                onClick = { "https://enderhoshi.github.io/".openBrowse() }
                            ) {
                                Icon(
                                    imageVector = IconRes.Blog,
                                    contentDescription = "blog",
                                    modifier = Modifier.size(20.dp),
                                    tint = ColorRes.textPrimary
                                )
                            }
                            ActionIconButton(
                                onClick = { "https://github.com/EndeRHoshI/HoshiDestopTools".openBrowse() }
                            ) {
                                Icon(
                                    imageVector = IconRes.Github,
                                    contentDescription = "github",
                                    modifier = Modifier.size(20.dp),
                                    tint = ColorRes.textPrimary
                                )
                            }
                        }
                    }
                }
            ) { innerPadding -> // 这个 innerPadding 是用来让 content 不和标题栏重叠的
                App(innerPadding)
            }
        }
    }
}

// Common Wrapper
//   - App Theme
//   - Toast
@Composable
fun AppContentWrapper(
    content: @Composable () -> Unit,
) {
    content()
    /*ToasterContainer {
    }*/
}