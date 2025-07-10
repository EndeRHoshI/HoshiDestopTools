package org.hoshi.destoptools

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import hoshidestoptools.composeapp.generated.resources.Res
import hoshidestoptools.composeapp.generated.resources.icon
import org.jetbrains.compose.resources.painterResource

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "HoshiDestopTools",
        icon = painterResource(Res.drawable.icon) // 注意这个是窗口的图标，和应用图标不同，完全两码事
    ) {
        App()
    }
}