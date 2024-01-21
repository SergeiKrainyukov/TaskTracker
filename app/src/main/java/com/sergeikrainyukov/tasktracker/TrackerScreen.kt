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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sergeikrainyukov.tasktracker.viewModels.TrackerScreenViewModel

@Composable
fun TrackerScreen(viewModel: TrackerScreenViewModel, navController: NavController) {
    val time = viewModel.trackerLiveData.observeAsState(0)
    val isStarted = viewModel.isStarted.observeAsState(false)

    val showDialog = remember { mutableStateOf(false) }
    val textState1 = remember { mutableStateOf("") }

    // Форматирование времени для отображения
    val minutes = time.value / 60
    val seconds = if (time.value >= 60) time.value % 60 else time.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "%02d:%02d".format(minutes, seconds))

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
                                viewModel.saveTask(
                                    textState1.value,
                                    convertMillisToTimeString(time.value)
                                )
                                viewModel.onClickFinish()
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
                    viewModel.onClickPauseResume()
                },
                enabled = !isStarted.value,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Старт")
            }

            Button(
                onClick = {
                    viewModel.onClickPauseResume()
                },
                enabled = isStarted.value,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text("Пауза")
            }

            Button(
                onClick = {
                    showDialog.value = true
                    viewModel.onClickStop()
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Стоп")
            }
        }
    }
}

fun convertMillisToTimeString(sec: Long): String {
    val hours = sec / 3600
    val minutes = sec / 60
    return String.format("%02d:%02d:%02d", hours, minutes, if (sec >= 60) sec % 60 else sec)
}

