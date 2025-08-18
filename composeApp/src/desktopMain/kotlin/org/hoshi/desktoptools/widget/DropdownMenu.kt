package org.hoshi.desktoptools.widget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.hoshi.desktoptools.data.TimeUnit

@Composable
fun DropdownMenu(currentUnit: MutableState<TimeUnit>) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Button(
            modifier = Modifier.padding(10.dp, 0.dp)
                .defaultMinSize(1.dp, 1.dp)
                .size(50.dp, 30.dp),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(4.dp),
            onClick = { expanded = !expanded }
        ) {
            Text(
                when (currentUnit.value) {
                    TimeUnit.MILLIS -> "毫秒"
                    TimeUnit.SEC -> "秒"
                }
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("毫秒") },
                onClick = {
                    currentUnit.value = TimeUnit.MILLIS
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("秒") },
                onClick = {
                    currentUnit.value = TimeUnit.SEC
                    expanded = false
                }
            )
        }
    }
}