package com.sergeikrainyukov.tasktracker.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taskEntity: TaskEntity)

    @Query("SELECT * FROM taskentity")
    fun getAll(): LiveData<List<TaskEntity>>

}