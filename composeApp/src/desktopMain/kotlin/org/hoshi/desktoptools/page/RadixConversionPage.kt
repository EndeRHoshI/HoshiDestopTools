package org.hoshi.desktoptools.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Checkbox
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.hoshi.desktoptools.widget.SingleConfirmDialog
import java.math.BigInteger

@Composable
fun RadixConversionPage() {
    var sourceNumber by remember { mutableStateOf("") }
    var targetNumber by remember { mutableStateOf("") }
    val sourceRadix = remember { mutableStateOf(10) } // 原始进制，默认 10
    val targetRadix = remember { mutableStateOf(10) } // 目标进制，默认 10
    val openAlertDialog = remember { mutableStateOf<Pair<String, String>?>(null) }

    fun inputSourceNumber(number: String) {
        sourceNumber = number
        targetNumber = convert(
            number,
            sourceRadix.value,
            targetRadix.value,
            openAlertDialog
        )
    }

    fun updateTargetNumber() {
        targetNumber = convert(
            sourceNumber,
            sourceRadix.value,
            targetRadix.value,
            openAlertDialog
        )
    }


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 22.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column {

                    Row(
                        modifier = Modifier
                    ) {
                        RadixCheckText(2, sourceRadix) { inputSourceNumber("") }
                        RadixCheckText(4, sourceRadix) { inputSourceNumber("") }
                        RadixCheckText(8, sourceRadix) { inputSourceNumber("") }
                        RadixCheckText(10, sourceRadix) { inputSourceNumber("") }
                        RadixCheckText(16, sourceRadix) { inputSourceNumber("") }
                        RadixCheckText(32, sourceRadix) { inputSourceNumber("") }
                    }

                    Row(
                        modifier = Modifier.padding(20.dp, 0.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text("转换数字")
                        Spacer(Modifier.width(12.dp))
                        BasicTextField(
                            value = sourceNumber,
                            onValueChange = {
                                inputSourceNumber(it)
                            },
                            modifier = Modifier
                                .background(Color.White, RoundedCornerShape(4.dp))
                                .height(35.dp)
                                .fillMaxWidth(),
                            decorationBox = { innerTextField ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                ) {
                                    Box(
                                        modifier = Modifier.weight(1f),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        innerTextField()
                                    }
                                }
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.padding(0.dp, 52.dp, 0.dp, 0.dp)
                    ) {
                        RadixCheckText(2, targetRadix) { updateTargetNumber() }
                        RadixCheckText(4, targetRadix) { updateTargetNumber() }
                        RadixCheckText(8, targetRadix) { updateTargetNumber() }
                        RadixCheckText(10, targetRadix) { updateTargetNumber() }
                        RadixCheckText(16, targetRadix) { updateTargetNumber() }
                        RadixCheckText(32, targetRadix) { updateTargetNumber() }
                    }

                    Row(
                        modifier = Modifier.padding(20.dp, 0.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text("转换结果")
                        Spacer(Modifier.width(12.dp))
                        BasicTextField(
                            value = targetNumber,
                            onValueChange = { targetNumber = it },
                            modifier = Modifier
                                .background(Color.White, RoundedCornerShape(4.dp))
                                .height(35.dp)
                                .fillMaxWidth(),
                            decorationBox = { innerTextField ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                ) {
                                    Box(
                                        modifier = Modifier.weight(1f),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        innerTextField()
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
        SingleConfirmDialog(openAlertDialog)
    }
}


fun convert(
    sourceNumber: String,
    sourceRadix: Int,
    targetRadix: Int,
    openAlertDialog: MutableState<Pair<String, String>?>
): String {
    require(sourceRadix in 2..36) { "原始进制必须在 2~36 之间" }
    require(targetRadix in 2..36) { "目标进制必须在 2~36 之间" }

    // 处理空字符串特殊情况
    if (sourceNumber.isEmpty()) return ""

    // 使用 BigInteger 处理任意精度的进制转换
    val isNegative = sourceNumber.startsWith("-")
    val number = if (isNegative) sourceNumber.substring(1) else sourceNumber

    // 处理全为0的特殊情况
    if (number.all { it == '0' }) return if (isNegative) "-0" else "0"

    return runCatching {
        // 转换为十进制中间值
        val decimalValue = BigInteger(number, sourceRadix)

        // 转换为目标进制
        var result = decimalValue.toString(targetRadix).uppercase()

        // 恢复负号
        if (isNegative && decimalValue != BigInteger.ZERO) {
            result = "-$result"
        }

        result
    }.onFailure {
        openAlertDialog.value = Pair("无效输入", "'${sourceNumber}' 不是有效的 $sourceRadix 进制数")
    }.getOrNull().orEmpty()
}

@Composable
fun RadixCheckText(
    radix: Int,
    currentRadix: MutableState<Int>,
    checkAction: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(12.dp, 0.dp)
    ) {
        Checkbox(
            radix == currentRadix.value,
            onCheckedChange = {
                if (it) { // 如果勾选才做出反应，反选不处理
                    currentRadix.value = radix
                    checkAction.invoke(radix)
                }
            }
        )
        Text("$radix 进制")
    }
}