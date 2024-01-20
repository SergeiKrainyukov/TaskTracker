package com.sergeikrainyukov.tasktracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sergeikrainyukov.tasktracker.db.AppDatabase
import com.sergeikrainyukov.tasktracker.db.DbProvider
import com.sergeikrainyukov.tasktracker.ui.theme.TaskTrackerTheme
import com.sergeikrainyukov.tasktracker.viewModels.TasksScreenViewModel
import com.sergeikrainyukov.tasktracker.viewModels.TrackerScreenViewModel

class MainActivity : ComponentActivity() {

    private lateinit var trackerScreenViewModel: TrackerScreenViewModel
    private lateinit var tasksScreenViewModel: TasksScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        trackerScreenViewModel = ViewModelProvider(
            this,
            ViewModelFactory(DbProvider.provideAppDataBase(context = this))
        )[TrackerScreenViewModel::class.java]

        tasksScreenViewModel = ViewModelProvider(
            this,
            ViewModelFactory(DbProvider.provideAppDataBase(context = this))
        )[TasksScreenViewModel::class.java]

        setContent {
            TaskTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(trackerScreenViewModel, tasksScreenViewModel)
                }
            }
        }
    }
}

@Composable
fun App(
    trackerScreenViewModel: TrackerScreenViewModel,
    tasksScreenViewModel: TasksScreenViewModel,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "menu") {
        composable("menu") { MenuScreen(navController) }
        composable("tasks") {
            TasksScreen(tasksScreenViewModel)
        }
        composable("tracker") {
            TrackerScreen(trackerScreenViewModel, navController)
        }
    }
}

class ViewModelFactory(
    private val appDatabase: AppDatabase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrackerScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TrackerScreenViewModel(appDatabase) as T
        }
        if (modelClass.isAssignableFrom(TasksScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TasksScreenViewModel(appDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
