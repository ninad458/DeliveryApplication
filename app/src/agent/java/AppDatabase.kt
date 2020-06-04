package com.enigma.myapplication

import android.app.Activity
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tasksDao(): TaskDao
}

fun Activity.getAppDB() =
    (application as? App)?.appDB ?: throw Exception("DB not initialised at App or app is null")

val Context.createRoom: AppDatabase
    get() = Room.databaseBuilder(
        this,
        AppDatabase::class.java, "database-name"
    ).build()