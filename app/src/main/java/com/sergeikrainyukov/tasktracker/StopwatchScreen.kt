package com.sergeikrainyukov.tasktracker

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun StopwatchScreen() {
    var time by remember { mutableStateOf(0L) } // время в миллисекундах
    var isRunning by remember { mutableStateOf(false) } // состояние секундомера
    val scope = rememberCoroutineScope()

    // Форматирование времени для отображения
    val minutes = (time / 1000) / 60
    val seconds = (time / 1000) % 60
    val milliseconds = time % 1000 / 10

    LaunchedEffect(isRunning) {
        while (isRunning && isActive) {
            delay(10)
            time += 10
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "%02d:%02d:%02d".format(minutes, seconds, milliseconds))

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(
                onClick = {
                    isRunning = true
                },
                enabled = !isRunning,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Старт")
            }

            Button(
                onClick = {
                    isRunning = false
                },
                enabled = isRunning,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text("Пауза")
            }

            Button(
                onClick = {
                    isRunning = false
                    time = 0L
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Стоп")
            }
        }
    }
}