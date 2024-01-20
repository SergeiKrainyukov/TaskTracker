package com.sergeikrainyukov.tasktracker.viewModels

import androidx.lifecycle.ViewModel
import com.sergeikrainyukov.tasktracker.db.AppDatabase

class TasksScreenViewModel(private val appDatabase: AppDatabase) : ViewModel() {
    fun getTasks() = appDatabase.taskEntityDao().getAll()
}