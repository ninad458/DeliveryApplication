package com.enigma.myapplication

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.enigma.myapplication.TaskActivity.Companion.launchTaskActivity
import kotlinx.android.synthetic.agent.activity_tasks.*

class TasksActivity : BaseActivity() {

    private val navigationListener = { id: String -> launchTaskActivity(id) }

    private val api by lazy { Api.getApi() }

    private val database by lazy { getAppDB().tasksDao() }

    private val tasks by lazy { database.getAll() }

    private val adapter = TasksAdapter(navigationListener)

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
            Log.d("zzzzzz", it.toString())
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