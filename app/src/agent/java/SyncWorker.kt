package com.enigma.myapplication

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class SyncWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    companion object {
        const val TAG = "SyncWorker"
    }

    private val appDB = context.createRoom.tasksDao()

    private val api = Api.getApi()

    override suspend fun doWork(): Result = coroutineScope {
        while (appDB.getAllTasksWithSync(false).isNotEmpty()) {
            val tasksWithSync = appDB.getAllTasksWithSync(false)
            val jobs = tasksWithSync.map { task ->
                async {
                    val response = api.updateTaskStatus(
                        TaskStatusRequestBody(
                            task.id,
                            task.status
                        )
                    )
                    Log.d(TAG, response.toString())
                    appDB.update(task.copy(synced = true))
                }
            }
            jobs.awaitAll()
        }
        Result.success()
    }
}