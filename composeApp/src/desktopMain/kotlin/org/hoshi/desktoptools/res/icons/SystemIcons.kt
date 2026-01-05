package org.hoshi.desktoptools.res.icons// SystemIcons.kt - 使用 Android 系统内置图标
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object SystemIcons {
    // 刷新图标
    val Refresh = ImageVector.Builder(
        name = "refresh",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = null,
            stroke = null,
            strokeLineWidth = 2.0f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
            strokeLineMiter = 4.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(23.0f, 4.0f)
            lineTo(23.0f, 10.0f)
            lineTo(17.0f, 10.0f)
            moveTo(1.0f, 20.0f)
            lineTo(1.0f, 14.0f)
            lineTo(7.0f, 14.0f)
            moveTo(3.51f, 9.0f)
            arcToRelative(9.0f, 9.0f, 0.0f, false, true, 14.85f, -3.36f)
            lineTo(23.0f, 10.0f)
            moveTo(1.0f, 14.0f)
            lineToRelative(4.64f, 4.36f)
            arcTo(9.0f, 9.0f, 0.0f, false, false, 20.49f, 15.0f)
        }
    }.build()
    
    // 搜索图标
    val Search = ImageVector.Builder(
        name = "search",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = null,
            stroke = null,
            strokeLineWidth = 2.0f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
            strokeLineMiter = 4.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(21.0f, 21.0f)
            lineToRelative(-6.0f, -6.0f)
            moveTo(11.0f, 4.0f)
            arcToRelative(7.0f, 7.0f, 0.0f, true, true, 0.0f, 14.0f)
            arcToRelative(7.0f, 7.0f, 0.0f, false, true, 0.0f, -14.0f)
        }
    }.build()
    
    // 错误图标
    val Error = ImageVector.Builder(
        name = "error",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = null,
            stroke = null,
            strokeLineWidth = 2.0f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
            strokeLineMiter = 4.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(12.0f, 8.0f)
            lineTo(12.0f, 12.0f)
            moveTo(12.0f, 16.0f)
            lineTo(12.01f, 16.0f)
            moveTo(12.0f, 3.0f)
            arcToRelative(9.0f, 9.0f, 0.0f, true, false, 0.0f, 18.0f)
            arcToRelative(9.0f, 9.0f, 0.0f, false, false, 0.0f, -18.0f)
        }
    }.build()
    
    // 设备图标
    val Devices = ImageVector.Builder(
        name = "devices",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = null,
            stroke = null,
            strokeLineWidth = 2.0f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
            strokeLineMiter = 4.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(3.0f, 19.0f)
            horizontalLineToRelative(18.0f)
            moveTo(5.0f, 7.0f)
            verticalLineToRelative(9.0f)
            horizontalLineToRelative(14.0f)
            verticalLineToRelative(-9.0f)
            close()
            moveTo(10.0f, 3.0f)
            horizontalLineToRelative(4.0f)
            verticalLineToRelative(4.0f)
            horizontalLineToRelative(-4.0f)
            close()
        }
    }.build()
    
    // 手机图标
    val PhoneAndroid = ImageVector.Builder(
        name = "phone_android",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = null,
            stroke = null,
            strokeLineWidth = 2.0f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
            strokeLineMiter = 4.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(18.0f, 2.0f)
            horizontalLineTo(6.0f)
            arcTo(2.0f, 2.0f, 0.0f, false, false, 4.0f, 4.0f)
            verticalLineTo(20.0f)
            arcTo(2.0f, 2.0f, 0.0f, false, false, 6.0f, 22.0f)
            horizontalLineTo(18.0f)
            arcTo(2.0f, 2.0f, 0.0f, false, false, 20.0f, 20.0f)
            verticalLineTo(4.0f)
            arcTo(2.0f, 2.0f, 0.0f, false, false, 18.0f, 2.0f)
            close()
            moveTo(12.0f, 20.5f)
            arcToRelative(1.5f, 1.5f, 0.0f, true, true, 0.0f, -3.0f)
            arcToRelative(1.5f, 1.5f, 0.0f, false, true, 0.0f, 3.0f)
            close()
            moveTo(15.5f, 17.0f)
            horizontalLineTo(8.5f)
            verticalLineTo(6.0f)
            horizontalLineToRelative(7.0f)
            close()
        }
    }.build()
}