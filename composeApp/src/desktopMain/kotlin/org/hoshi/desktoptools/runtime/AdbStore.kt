package org.hoshi.desktoptools.runtime

import hoshidesktoptools.composeapp.generated.resources.Res
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.hoshi.desktoptools.utils.ZipUtils
import org.jetbrains.skiko.hostOs
import java.io.ByteArrayInputStream
import java.io.File
import java.nio.file.Paths

/**
 * ADB 仓库，用来存一些 ADB 相关的内容
 */
class AdbStore() {

    private val rootPath: String = ".fileManager" // 本项目的根目录，后续可以改为 desktopTools 之类的，暂时先不管

    private val resourcePath by lazy {
        val hostName = when {
            hostOs.isWindows -> "windows"
            hostOs.isMacOS -> "macos"
            else -> "linux"
        }
        val adb = if (hostOs.isWindows) "adb.zip" else "adb"
        "files/$hostName/$adb"
    }

    // 缓存目录，根据系统放置，Caches 下的 files 目录
    // Mac 形如/Users/lvqixing/Library/Caches/$rootPath/files/
    // Windows 还未尝试
    val cacheFileDir by lazy {
        val cachePath = when {
            hostOs.isWindows -> System.getenv("LOCALAPPDATA") ?: Paths.get(
                System.getProperty("user.home"),
                "AppData",
                "Local"
            ).toString()

            hostOs.isMacOS -> Paths.get(System.getProperty("user.home"), "Library", "Caches")
                .toString()

            else -> Paths.get(System.getProperty("user.home"), ".cache").toString()
        }

        val cacheDir = File(cachePath, rootPath)
        if (!cacheDir.exists()) cacheDir.mkdirs()

        val file = File(cacheDir, "files")
        if (!file.exists()) file.mkdirs()
        file
    }

    suspend fun installRuntime() {
        val installPath = cacheFileDir.absolutePath
        withContext(Dispatchers.IO) {
            ByteArrayInputStream(Res.readBytes(resourcePath)).use { inputStream ->
                if (hostOs.isWindows) { // 如果是 Windows 系统，解压
                    ZipUtils.unzip(inputStream, installPath)
                    return@withContext
                } else { // 如果是 Mac 系统，直接复制
                    val adbOutputFile = File(installPath, "adb")
                    if (!adbOutputFile.exists()) {
                        adbOutputFile.createNewFile()
                    }
                    adbOutputFile.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            }
        }
    }

}