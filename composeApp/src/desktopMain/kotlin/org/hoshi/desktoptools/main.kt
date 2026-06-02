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
import org.hoshi.desktoptools.utils.AdbHelper
import org.hoshi.desktoptools.widget.ActionIconButton
import org.hoshi.desktoptools.widget.WindowTopBar
import org.hoshi.desktoptools.window.AppWindow
import org.jetbrains.compose.resources.painterResource

/**
 * 专门针对 macOS 平台的优化方法：用于在开发调试运行时，将 Dock 栏默认的 Java (Duke) 图标替换为我们自定义的星星图标。
 * 
 * 为什么需要这个方法？
 * - 在 macOS 上开发调试 Java/Kotlin 桌面程序时，JVM 默认启动的是一个通用的 java 进程，Dock 栏默认会显示小怪兽（Duke）或咖啡杯。
 * - Compose Window 属性中的 icon 只能设置窗口本身的图标（且在无边框窗体下不可见），无法自动改变 Mac 系统的 Dock 栏图标。
 * - 因此，我们需要在程序刚启动时，通过 Java AWT 原生的 Taskbar API 动态地将我们最新的星星图片设置进系统 Dock 栏中。
 */
private fun setMacDockIcon() {
    try {
        // 1. 获取当前操作系统的名称
        val osName = System.getProperty("os.name")

        // 2. 仅在 macOS 系统下执行此逻辑，防止在 Windows 或 Linux 上报错
        if (osName.startsWith("Mac", ignoreCase = true)) {
            // 3. 检查当前 JVM 运行时环境是否支持 Taskbar API（部分老旧或精简版 JDK 可能不支持）
            if (java.awt.Taskbar.isTaskbarSupported()) {
                val taskbar = java.awt.Taskbar.getTaskbar()

                // 4. 检查系统任务栏是否支持修改图标特征
                if (taskbar.isSupported(java.awt.Taskbar.Feature.ICON_IMAGE)) {
                    // 5. 定义新图标在编译后 classpath 中的相对资源路径
                    val resourcePath = "composeResources/hoshidesktoptools.composeapp.generated.resources/drawable/icon.png"

                    // 6. 使用当前线程的类加载器（ClassLoader）读取该文件输入流
                    val stream = Thread.currentThread().contextClassLoader.getResourceAsStream(resourcePath)
                    if (stream != null) {
                        // 7. 读取全部字节并使用 AWT Toolkit 创建原生图像对象，这能更好地让 macOS 保留 Alpha 透明通道
                        val bytes = stream.readBytes()
                        val image = java.awt.Toolkit.getDefaultToolkit().createImage(bytes)

                        // 8. 将图片设置给 macOS 系统的 Dock 栏任务管理器
                        taskbar.iconImage = image
                    }
                }
            }
        }
    } catch (e: Throwable) {
        // 9. 捕获所有可能出现的异常或错误（例如安全管理器拦截、无头环境等），确保即使图标加载失败，应用也绝不会崩溃
    }
}

fun main() {
    setMacDockIcon()
    application {
        // 初始化 ADB 运行时环境
        AdbHelper.initAdbRuntime { adbPath ->
            println("初始化 ADB 运行时环境成功，adbPath = $adbPath")
        }
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