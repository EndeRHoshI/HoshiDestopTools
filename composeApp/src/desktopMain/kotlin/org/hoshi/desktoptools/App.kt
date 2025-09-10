package org.hoshi.desktoptools

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.hoshi.desktoptools.page.Page
import org.hoshi.desktoptools.res.ColorRes
import org.hoshi.desktoptools.res.Dimens
import org.hoshi.desktoptools.window.LocalWindowScope
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(innerPadding: PaddingValues) {
    val windowScope = LocalWindowScope.current
    var page by remember { mutableStateOf(Page.Home) } // 当前页面
    Row(Modifier.background(ColorRes.bgSecondary)) {
        windowScope.WindowDraggableArea { // 可拖拽操作区域
            Column(
                modifier = Modifier.fillMaxHeight().width(80.dp).padding(top = 100.dp).background(ColorRes.bgSecondary)
            ) {
                Spacer(Modifier.height(20.dp))
                Page.entries.forEach { currentPage -> // 遍历 Page 枚举类里面的页面类，加载各个页面按钮
                    PageIcon(currentPage) { page = it }
                }
            }
        }
        // 页面的主要区域
        Box(Modifier.background(ColorRes.bgPrimary).padding(innerPadding)) {
            page.content.invoke()
        }
    }
    // 从外部处理一下标题栏里面的标题，真正标题栏里面那个不太好处理
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.height(Dimens.titleBarHeight).padding(start = 110.dp)
    ) {
        Text(page.title, fontSize = 16.sp, color = ColorRes.textPrimary)
    }
}

/**
 * 切换页面的 Icon
 * @param page 对应的页面实体类
 * @param onClick 点击事件
 */
@Composable
fun PageIcon(page: Page, onClick: (Page) -> Unit) {
    Box(
        Modifier.fillMaxWidth().clickable { onClick.invoke(page) },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(page.icon),
            "",
            modifier = Modifier.padding(30.dp, 18.dp),
            tint = ColorRes.textPrimary
        )
    }
}