package com.sergeikrainyukov.tasktracker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sergeikrainyukov.tasktracker.viewModels.TasksScreenViewModel
import androidx.compose.runtime.livedata.observeAsState
import java.text.SimpleDateFormat
import java.util.Date

fun convertMillisToDateStr(millis: Long): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy")
    val date = Date(millis)
    return formatter.format(date)
}

@Composable
fun TasksScreen(viewModel: TasksScreenViewModel) {

    val words by viewModel.getTasks().observeAsState(initial = emptyList())

    val cardItems = words.map {
        CardItem(
            title = it.name,
            time = it.time,
            date = convertMillisToDateStr(it.date)
        )
    }
    CardsList(cardItems)
}

data class CardItem(
    val title: String,
    val time: String,
    val date: String,
)

@Composable
fun CardsList(cardItems: List<CardItem>) {
    LazyColumn {
        items(cardItems) { cardItem ->
            CardItemView(cardItem) {}
        }
    }
}

@Composable
fun CardItemView(cardItem: CardItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = cardItem.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = cardItem.time,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = cardItem.date,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}