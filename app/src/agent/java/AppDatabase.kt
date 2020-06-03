package com.enigma.myapplication

import android.app.Activity
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tasksDao(): TaskDao
}

fun Activity.getAppDB() =
    (application as? App)?.getAppDB() ?: throw Exception("DB not initialised at App")