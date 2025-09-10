package org.hoshi.desktoptools.res.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import org.hoshi.desktoptools.res.IconRes

val IconRes.Blog: ImageVector
    get() {
        if (_Blog != null) {
            return _Blog!!
        }
        _Blog = ImageVector.Builder(
            name = "Blog",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE3E3E3))) {
                moveTo(19f, 1f)
                lineToRelative(-5f, 5f)
                verticalLineToRelative(11f)
                lineToRelative(5f, -4.5f)
                lineTo(19f, 1f)
                close()
                moveTo(1f, 6f)
                verticalLineToRelative(14.65f)
                curveToRelative(0f, 0.25f, 0.25f, 0.5f, 0.5f, 0.5f)
                curveToRelative(0.1f, 0f, 0.15f, -0.05f, 0.25f, -0.05f)
                curveTo(3.1f, 20.45f, 5.05f, 20f, 6.5f, 20f)
                curveToRelative(1.95f, 0f, 4.05f, 0.4f, 5.5f, 1.5f)
                lineTo(12f, 6f)
                curveToRelative(-1.45f, -1.1f, -3.55f, -1.5f, -5.5f, -1.5f)
                reflectiveCurveTo(2.45f, 4.9f, 1f, 6f)
                close()
                moveTo(23f, 19.5f)
                lineTo(23f, 6f)
                curveToRelative(-0.6f, -0.45f, -1.25f, -0.75f, -2f, -1f)
                verticalLineToRelative(13.5f)
                curveToRelative(-1.1f, -0.35f, -2.3f, -0.5f, -3.5f, -0.5f)
                curveToRelative(-1.7f, 0f, -4.15f, 0.65f, -5.5f, 1.5f)
                verticalLineToRelative(2f)
                curveToRelative(1.35f, -0.85f, 3.8f, -1.5f, 5.5f, -1.5f)
                curveToRelative(1.65f, 0f, 3.35f, 0.3f, 4.75f, 1.05f)
                curveToRelative(0.1f, 0.05f, 0.15f, 0.05f, 0.25f, 0.05f)
                curveToRelative(0.25f, 0f, 0.5f, -0.25f, 0.5f, -0.5f)
                verticalLineToRelative(-1.1f)
                close()
            }
        }.build()

        return _Blog!!
    }

@Suppress("ObjectPropertyName")
private var _Blog: ImageVector? = null
