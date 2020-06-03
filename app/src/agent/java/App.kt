package com.enigma.myapplication

import android.app.Application
import androidx.room.Room

class App : Application() {

    private val db by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database-name"
        ).build()
    }

    fun getAppDB() = db
}