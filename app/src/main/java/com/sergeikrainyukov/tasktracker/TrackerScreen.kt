package com.sergeikrainyukov.tasktracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sergeikrainyukov.tasktracker.viewModels.TrackerScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun TrackerScreen(trackerScreenViewModel: TrackerScreenViewModel, navController: NavController) {
    var time by remember { mutableLongStateOf(0L) } // время в миллисекундах
    var isRunning by remember { mutableStateOf(false) } // состояние секундомера

    val showDialog = remember { mutableStateOf(false) }
    val textState1 = remember { mutableStateOf("") }


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
                    title = { Text("Задача") },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = textState1.value,
                                onValueChange = { textState1.value = it },
                                label = { Text("Описание задачи") }
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                trackerScreenViewModel.saveTask(textState1.value, convertMillisToTimeString(time))
                                showDialog.value = false
                                navController.popBackStack()
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
//                    time = 0L
                    showDialog.value = true
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Стоп")
            }
        }
    }
}

fun convertMillisToTimeString(millis: Long): String {
    val hours = millis / (1000 * 60 * 60) % 24
    val minutes = millis / (1000 * 60) % 60
    val seconds = millis / 1000 % 60

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

