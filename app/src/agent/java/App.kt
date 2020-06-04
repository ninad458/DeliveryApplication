package com.enigma.myapplication

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.enigma.myapplication.data.remote.sync.SyncWorker
import com.enigma.myapplication.data.local.createRoom

class App : Application() {

    val appDB by lazy { createRoom }

    override fun onCreate() {
        super.onCreate()
        appDB.tasksDao().getAll().observeForever {
            println("zzzzzzzzz")
            val constraints: Constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val onetimeJob: OneTimeWorkRequest = OneTimeWorkRequest.Builder(SyncWorker::class.java)
                .setConstraints(constraints).build() // or PeriodicWorkRequest

            WorkManager.getInstance(this).enqueue(onetimeJob)
        }
    }
}