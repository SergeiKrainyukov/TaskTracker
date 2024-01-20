package com.sergeikrainyukov.tasktracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MenuScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Определяем общий модификатор для кнопок
            val buttonModifier = Modifier
                .fillMaxWidth() // Заполняем всю доступную ширину
                .padding(horizontal = 16.dp) // Добавляем горизонтальный отступ

            Button(
                onClick = { navController.navigate("tracker") },
                modifier = buttonModifier
            ) {
                Text("Секундомер", fontSize = 22.sp)
            }

            Button(
                onClick = { navController.navigate("tasks") },
                modifier = buttonModifier
            ) {
                Text("Задачи", fontSize = 22.sp)
            }
        }
    }
}