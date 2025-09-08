package org.hoshi.desktoptools.res

import androidx.compose.ui.graphics.vector.ImageVector
import org.hoshi.desktoptools.res.icons.Github

object IconRes

private var all: List<ImageVector>? = null

val IconRes.All: List<ImageVector>
    get() {
        return all ?: listOf(
            Github,
        ).also { all = it }
    }
