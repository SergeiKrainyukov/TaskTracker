package com.sergeikrainyukov.tasktracker.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergeikrainyukov.tasktracker.TimeTracker
import com.sergeikrainyukov.tasktracker.TimeTrackerImpl
import com.sergeikrainyukov.tasktracker.db.AppDatabase
import com.sergeikrainyukov.tasktracker.db.TaskEntity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar

class TrackerScreenViewModel(private val appDatabase: AppDatabase) : ViewModel() {

    val trackerLiveData: MutableLiveData<Long> = MutableLiveData()
    val isStarted: MutableLiveData<Boolean> = MutableLiveData()

    private val timeTracker: TimeTracker = TimeTrackerImpl()
    private var _timeCountState = 0L

    init {
        viewModelScope.launch {
            timeTracker.listen().collectLatest {
                _timeCountState = it
                trackerLiveData.value = _timeCountState
            }
        }

    }

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

    fun onClickStop() {
        timeTracker.stopTimer()
        isStarted.value = false
    }

    fun onClickFinish() {
        timeTracker.finish()
        trackerLiveData.value = 0
    }

    fun onClickPauseResume() {
        isStarted.value = if (timeTracker.isRunning()) {
            timeTracker.pause()
            false
        } else {
            timeTracker.resume()
            true
        }
    }
}