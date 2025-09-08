package org.hoshi.desktoptools.widget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hoshidesktoptools.composeapp.generated.resources.Res
import hoshidesktoptools.composeapp.generated.resources.ic_close
import hoshidesktoptools.composeapp.generated.resources.ic_minimized
import org.hoshi.desktoptools.window.LocalWindowScope
import org.jetbrains.compose.resources.painterResource

@Composable
fun ActionIconButton(
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable () -> Unit,
) {
    CardButton(
        modifier = modifier.padding(horizontal = 4.dp),
        shape = CircleShape,
        contentPadding = PaddingValues(12.dp),
        contentColor = LocalContentColor.current,
        backgroundColor = Color.Transparent,
        onClick = onClick,
        onLongClick = onLongClick,
        enabled = enabled,
        content = icon,
    )
}

/**
 * 窗口顶部标题栏
 * @param onCloseRequest 关闭事件
 * @param title 标题布局
 * @param actions 除了最小化、关闭之外的其他控件（可以继续加各种按钮）
 */
@Composable
fun WindowTopBar(
    onCloseRequest: () -> Unit,
    title: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    val current = LocalWindowScope.current
    current.WindowDraggableArea {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            title()
            Spacer(modifier = Modifier.weight(1f))
            actions()
            ActionIconButton(
                onClick = {
                    current.window.isMinimized = true
                },
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_minimized),
                    contentDescription = "minimized",
                    modifier = Modifier.size(20.dp)
                )
            }
            ActionIconButton(
                onClick = onCloseRequest,
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_close),
                    contentDescription = "close",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}