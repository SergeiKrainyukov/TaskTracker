package com.sergeikrainyukov.tasktracker

import kotlinx.coroutines.flow.StateFlow

interface TimeTracker {
    fun startTimer()
    fun stopTimer()
    fun pause()
    fun resume()
    fun listen(): StateFlow<Long>
    fun getActualValue(): Long
    fun isRunning(): Boolean
    fun finish()
}