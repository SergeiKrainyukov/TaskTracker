package com.sergeikrainyukov.tasktracker

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sergeikrainyukov.tasktracker.viewModels.TrackerScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun TrackerScreen(trackerScreenViewModel: TrackerScreenViewModel) {
    var time by remember { mutableLongStateOf(0L) } // время в миллисекундах
    var isRunning by remember { mutableStateOf(false) } // состояние секундомера

    val showDialog = remember { mutableStateOf(false) }
    val textState1 = remember { mutableStateOf("") }
    val textState2 = remember { mutableStateOf("") }


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
            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text("Заголовок диалога") },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = textState1.value,
                                onValueChange = { textState1.value = it },
                                label = { Text("Текстовое поле 1") }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = textState2.value,
                                onValueChange = { textState2.value = it },
                                label = { Text("Текстовое поле 2") }
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                // Обработка нажатия "ОК" (например, сохранение данных)
                                showDialog.value = false
                            }
                        ) {
                            Text("ОК")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                // Обработка нажатия "Отмена"
                                showDialog.value = false
                            }
                        ) {
                            Text("Отмена")
                        }
                    }
                )
            }
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
                    showDialog.value = true
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Стоп")
            }
        }
    }
}

