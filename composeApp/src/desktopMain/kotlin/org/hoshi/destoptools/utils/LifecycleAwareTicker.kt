package org.hoshi.destoptools.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LifecycleAwareTicker(
    private val scope: CoroutineScope,
    private val interval: Long = 1000 // 默认1秒
) {
    private val _tickerFlow = MutableStateFlow(System.currentTimeMillis())
    val tickerFlow: StateFlow<Long> = _tickerFlow.asStateFlow()

    private var job: Job? = null

    fun start() {
        stop() // 确保之前任务被取消
        job = scope.launch {
            while (true) {
                _tickerFlow.value = System.currentTimeMillis()
                delay(interval) // 使用自定义间隔
            }
        }
    }

    fun stop() {
        job?.cancel()
        job = null
    }
}

@Composable
fun rememberTicker(interval: Long = 1000): LifecycleAwareTicker {
    val scope = rememberCoroutineScope()
    val ticker = remember { LifecycleAwareTicker(scope, interval) }

    // 页面进入时启动
    DisposableEffect(Unit) {
        ticker.start()
        onDispose { ticker.stop() } // 页面退出时自动停止
    }

    return ticker
}