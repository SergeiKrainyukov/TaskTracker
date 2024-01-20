package com.sergeikrainyukov.tasktracker.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergeikrainyukov.tasktracker.db.AppDatabase
import com.sergeikrainyukov.tasktracker.db.TaskEntity
import kotlinx.coroutines.launch
import java.util.Calendar

class TrackerScreenViewModel(private val appDatabase: AppDatabase) : ViewModel() {
    fun saveTask(name: String, time: String) {
        viewModelScope.launch {
            if (name.isNotBlank() && time.isNotBlank())
                appDatabase.taskEntityDao().insert(
                    TaskEntity(
                        name = name,
                        time = time,
                        date = Calendar.getInstance().timeInMillis
                    )
                )
        }
    }
}