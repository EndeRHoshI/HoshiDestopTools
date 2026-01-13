package org.hoshi.desktoptools.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.hoshi.desktoptools.res.ColorRes
import org.hoshi.desktoptools.res.icons.SystemIcons
import org.hoshi.desktoptools.utils.AdbHelper
import org.hoshi.desktoptools.utils.DeviceInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdbPage() {

    var devices by remember { mutableStateOf<List<DeviceInfo>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var selectedDevice by remember { mutableStateOf<DeviceInfo?>(null) }
    var showDetails by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    // 加载设备列表
    fun loadDevices() {
        coroutineScope.launch {
            isLoading = true
            errorMessage = null
            runCatching {
                devices = AdbHelper.getConnectedDevices()
            }.onFailure {
                errorMessage = "无法加载设备列表: ${it.message}"
                devices = emptyList()
            }
            isLoading = false
        }
    }

    // 自动刷新逻辑
    LaunchedEffect(Unit) { // LaunchedEffect 作用：只有在当前页面内才会执行下面逻辑
        while (true) {
            loadDevices() // 每次进入页面都会刷新一下
            delay(2000) // 然后间隔一定时间刷新一次
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        when {
            isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("正在扫描 Android 设备...")
                }
            }

            errorMessage != null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = SystemIcons.Error,
                        contentDescription = "错误",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { loadDevices() }) {
                        Text("重试")
                    }
                }
            }

            devices.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = SystemIcons.Devices,
                        contentDescription = "无设备",
                        modifier = Modifier.size(96.dp),
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        "未检测到 Android 设备",
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "请确保：\n• Android 设备已通过 USB 连接\n• 已开启 USB 调试模式\n• 已安装正确的 USB 驱动程序\n• ADB 路径正确",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }

            else -> {
                Column {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(devices) { device ->
                            DeviceCard(
                                device = device,
                                onClick = {
                                    selectedDevice = device
                                    showDetails = true
                                }
                            )
                        }
                    }

                    Row {
                        TextButton(
                            modifier = Modifier,
                            onClick = { AdbHelper.executeAdbCommand("-s", devices.firstOrNull()?.id.orEmpty(), "shell", "wm size 720x1280") }
                        ) {
                            Text("设置分辨率", color = Color.White)
                        }
                        TextButton(
                            modifier = Modifier,
                            onClick = { AdbHelper.executeAdbCommand("-s", "8KE5T19731012277", "shell", "wm size reset") }
                        ) {
                            Text("还原分辨率", color = Color.White)
                        }
                        TextButton(
                            modifier = Modifier,
                            onClick = { AdbHelper.executeAdbCommand("-s", "8KE5T19731012277", "shell", "wm density 160") }
                        ) {
                            Text("设置 DPI", color = Color.White)
                        }
                        TextButton(
                            modifier = Modifier,
                            onClick = { AdbHelper.executeAdbCommand("-s", "8KE5T19731012277", "shell", "wm density reset") }
                        ) {
                            Text("还原 DPI", color = Color.White)
                        }
                    }
                }
            }
        }

        // 设备详情对话框
        if (showDetails && selectedDevice != null) {
            DeviceDetailsDialog(
                device = selectedDevice!!,
                onDismiss = {
                    showDetails = false
                    selectedDevice = null
                }
            )
        }
    }
}

@Composable
fun DeviceDetailItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
fun DeviceCard(
    device: DeviceInfo,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = ColorRes.bgSecondary
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = device.name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = device.model,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )

                Text(
                    text = "ID: ${device.id}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
            }
        }
    }
}

@Composable
fun DeviceDetailsDialog(
    device: DeviceInfo,
    onDismiss: () -> Unit
) {
    var deviceDetails by remember { mutableStateOf<Map<String, String>?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(device) {
        isLoading = true
        deviceDetails = AdbHelper.getDeviceInfo(device.id)
        isLoading = false
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("设备详情")
        },
        text = {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // 基本设备信息
                    DeviceDetailItem("设备名称", device.name)
                    DeviceDetailItem("设备型号", device.model)
                    DeviceDetailItem("设备ID", device.id)

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    // 动态获取的设备信息
                    deviceDetails?.forEach { (key, value) ->
                        if (value.isNotBlank()) {
                            DeviceDetailItem(key, value)
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("关闭")
            }
        }
    )
}