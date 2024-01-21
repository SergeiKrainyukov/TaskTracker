package com.sergeikrainyukov.tasktracker

import android.os.CountDownTimer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TimeTrackerImpl : TimeTracker, CountDownTimer(Long.MAX_VALUE, 1000) {

    private val timeState = MutableStateFlow(0L)
    private var elapsedTime = 0L
    private var isRunning = false

    override fun startTimer() {
        isRunning = true
        start()
    }

    override fun onTick(p0: Long) {
        elapsedTime += 1000
        timeState.value = elapsedTime / 1000
    }

    override fun onFinish() {
        isRunning = false
    }

    override fun stopTimer() {
        isRunning = false
        cancel()
    }

    override fun pause() {
        isRunning = false
        cancel()
    }

    override fun resume() {
        isRunning = true
        start()
    }

    override fun listen(): StateFlow<Long> = timeState

    override fun getActualValue(): Long {
        return timeState.value
    }

    override fun isRunning() = isRunning

    override fun finish() {
        isRunning = false
        timeState.value = 0
        elapsedTime = 0
        onFinish()
    }


}