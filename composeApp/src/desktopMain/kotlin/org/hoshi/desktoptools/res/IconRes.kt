package org.hoshi.desktoptools.res

import androidx.compose.ui.graphics.vector.ImageVector
import org.hoshi.desktoptools.res.icons.Github

/**
 * 这里的 IconRes 类，是使用 Valkyrie 插件引入矢量图生成的
 *
 * 使用 Valkyrie 插件，需要指定图标包路径 Package 和图标包名 Icon pack name，这样导入 SVG/XML 矢量图后
 * 会在对应文件夹下生成一些矢量图 ImageVector kt 文件和指定的 IconRes 类，后续使用矢量图就可以用 IconRes.xxxx 来用了
 */
object IconRes

private var all: List<ImageVector>? = null

val IconRes.All: List<ImageVector>
    get() {
        return all ?: listOf(
            Github,
        ).also { all = it }
    }
