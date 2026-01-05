package org.hoshi.desktoptools.utils

import java.io.BufferedReader
import java.io.InputStreamReader

object AdbHelper {

    /**
     * 获取 ADB 可执行文件路径
     */
    private fun getAdbPath(): String {
        return try {
            // 尝试从环境变量中获取 ADB 路径
            System.getenv("ANDROID_HOME")?.let {
                "$it/Android/sdk/platform-tools/adb"
            } ?: run {
                // 如果环境变量未设置，尝试使用默认路径或直接使用 adb 命令
                val home = System.getProperty("user.home")
                val defaultPaths = listOf(
                    "$home/Library/Android/sdk/platform-tools/adb",
                    "/opt/android-sdk/platform-tools/adb",
                    "adb" // 假设 adb 已在 PATH 中
                )

                defaultPaths.firstOrNull { path ->
                    try {
                        val process = ProcessBuilder(path, "version").start()
                        process.waitFor()
                        process.exitValue() == 0
                    } catch (e: Exception) {
                        false
                    }
                } ?: "adb"
            }
        } catch (e: Exception) {
            "adb" // 回退到直接使用 adb 命令
        }
    }

    /**
     * 执行 ADB 命令并返回结果
     */
    fun executeAdbCommand(vararg args: String): String {
        return runCatching {
            val adbPath = getAdbPath()
            val command = mutableListOf(adbPath).apply { addAll(args) }

            if (!args.contains("devices")) { // 过滤掉刷新设备列表的指令
                println("命令行指令：$command")
            }

            val process = ProcessBuilder(command)
                .redirectErrorStream(true)
                .start()

            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val output = StringBuilder()
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                output.appendLine(line)
            }

            process.waitFor()
            output.toString()
        }.onFailure {
            println("Error executing ADB command: ${it.message}")
        }.getOrNull().orEmpty()
    }

    /**
     * 获取已连接的 Android 设备列表
     * @return 设备信息列表，包含设备ID、名称和状态
     */
    fun getConnectedDevices(): List<DeviceInfo> {
        val output = executeAdbCommand("devices", "-l")
        return parseDevicesOutput(output)
    }

    /**
     * 解析 adb devices -l 的输出
     */
    private fun parseDevicesOutput(output: String): List<DeviceInfo> {
        val devices = mutableListOf<DeviceInfo>()

        output.lineSequence().forEachIndexed { index, line ->
            // 跳过标题行和空行
            if (index == 0 || line.isBlank() || line.startsWith("List of devices")) {
                return@forEachIndexed
            }

            // 解析设备信息
            val parts = line.split("\\s+".toRegex())
            if (parts.size >= 2) {
                val deviceId = parts[0]
                val status = parts[1]

                // 提取设备名称（如果有）
                var deviceName = "Unknown Device"
                var model = "Unknown Model"

                line.split(" ").forEach { part ->
                    when {
                        part.startsWith("model:") -> {
                            model = part.substringAfter("model:").replace("_", " ")
                        }

                        part.startsWith("device:") -> {
                            deviceName = part.substringAfter("device:")
                        }

                        part.startsWith("product:") -> {
                            deviceName = part.substringAfter("product:").replace("_", " ")
                        }
                    }
                }

                devices.add(
                    DeviceInfo(
                        id = deviceId,
                        name = deviceName.takeIf { it != "device:" } ?: model,
                        model = model,
                        status = status,
                        isOnline = status == "device"
                    )
                )
            }
        }

        return devices
    }

    /**
     * 获取设备详细信息
     */
    fun getDeviceInfo(deviceId: String): Map<String, String> {
        val info = mutableMapOf<String, String>()

        // 获取设备型号
        val modelOutput = executeAdbCommand("-s", deviceId, "shell", "getprop", "ro.product.model")
        info["Model"] = modelOutput.trim()

        // 获取 Android 版本
        val versionOutput = executeAdbCommand("-s", deviceId, "shell", "getprop", "ro.build.version.release")
        info["Android Version"] = versionOutput.trim()

        // 获取设备品牌
        val brandOutput = executeAdbCommand("-s", deviceId, "shell", "getprop", "ro.product.brand")
        info["Brand"] = brandOutput.trim()

        // 获取设备制造商
        val manufacturerOutput = executeAdbCommand("-s", deviceId, "shell", "getprop", "ro.product.manufacturer")
        info["Manufacturer"] = manufacturerOutput.trim()

        return info
    }

    /**
     * 刷新设备连接
     */
    fun refreshDevices(): List<DeviceInfo> {
        // 重启 ADB 服务以刷新设备列表
        executeAdbCommand("kill-server")
        Thread.sleep(1000) // 等待服务器关闭
        executeAdbCommand("start-server")
        Thread.sleep(2000) // 等待服务器启动并扫描设备

        return getConnectedDevices()
    }
}

data class DeviceInfo(
    val id: String,
    val name: String,
    val model: String,
    val status: String,
    val isOnline: Boolean
)