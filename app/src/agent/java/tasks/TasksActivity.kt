package com.enigma.myapplication.tasks

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.enigma.myapplication.BaseActivity
import com.enigma.myapplication.R
import com.enigma.myapplication.task.TaskActivity.Companion.launchTaskActivity
import com.enigma.myapplication.data.remote.api.Api
import com.enigma.myapplication.data.local.TaskEntity
import com.enigma.myapplication.data.local.getAppDB
import kotlinx.android.synthetic.agent.activity_tasks.*

class TasksActivity : BaseActivity() {

    companion object {
        private const val TAG = "TasksActivity"
    }

    private val navigationListener = { id: String -> launchTaskActivity(id) }

    private val api by lazy { Api.getApi() }

    private val database by lazy { getAppDB().tasksDao() }

    private val tasks by lazy { database.getAll() }

    private val adapter =
        TasksAdapter(navigationListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)
        loadList()
        loadTasks()
    }

    private fun loadList() {
        list.adapter = adapter
        list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        tasks.observe(this, Observer {
            Log.d(TAG, it.toString())
            adapter.setTasks(it)
        })
    }

    private fun loadTasks() {
        launch {
            val responseTasks = api.getTasks()
            database.insertAll(*responseTasks.orders.map {
                TaskEntity(
                    it.id,
                    it.status,
                    it.item,
                    it.details,
                    it.address,
                    it.name
                )
            }.toTypedArray())
        }
    }
}