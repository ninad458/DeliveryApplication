package com.enigma.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAll(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id == :id")
    fun getTask(id: String): LiveData<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg tasks: TaskEntity)

    @Update
    suspend fun update(vararg tasks: TaskEntity)

    @Query("SELECT * FROM tasks WHERE synced = :synced")
    suspend fun getAllTasksWithSync(synced: Boolean): List<TaskEntity>
}