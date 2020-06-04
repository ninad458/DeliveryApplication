package com.enigma.myapplication.task

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.enigma.myapplication.BaseActivity
import com.enigma.myapplication.R
import com.enigma.myapplication.data.remote.api.Status.CANCELLED
import com.enigma.myapplication.data.remote.api.Status.DELIVERED
import com.enigma.myapplication.data.remote.api.Status.IN_TRANSIT
import com.enigma.myapplication.data.remote.api.Status.QUEUED
import com.enigma.myapplication.data.local.TaskEntity
import com.enigma.myapplication.data.local.getAppDB
import com.enigma.myapplication.tasks.TasksActivity
import kotlinx.android.synthetic.agent.activity_task.*

class TaskActivity : BaseActivity() {

    private val appDB by lazy { getAppDB().tasksDao() }

    private val task by lazy { appDB.getTask(id) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        fetchAndSetTask()
    }

    private fun fetchAndSetTask() {
        task.observe(this, Observer {
            try {
                txt_id.text = it.id
                txt_item.text = it.item
                txt_description.text = it.details
                txt_address.text = it.address
                txt_name.text = it.name
                txt_delivery_status.text = it.status.name
                act_change_state.text = when (it.status) {
                    QUEUED -> "I have picked up the order"
                    IN_TRANSIT -> "I have delivered the order"
                    DELIVERED -> "Successfully delivered"
                    CANCELLED -> "Canceled"
                }
                if (it.status == DELIVERED || it.status == CANCELLED)
                    act_change_state.isEnabled = false
                else act_change_state.setOnClickListener { _ -> showPopUp(it) }
            } catch (e: Exception) {
                Log.e(TAG, e.message)
            }
        })
    }

    private fun showPopUp(task: TaskEntity) {
        AlertDialog.Builder(this)
            .setTitle("Title")
            .setMessage("Are you sure?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(
                android.R.string.yes
            ) { _, _ ->
                launch {
                    appDB.update(
                        task.copy(
                            status = when (task.status) {
                                QUEUED -> IN_TRANSIT
                                IN_TRANSIT -> DELIVERED
                                else -> throw Exception("Illegal state exception")
                            }, synced = false
                        )
                    )
                    finish()
                }
            }
            .setNegativeButton(android.R.string.no, null).show()
    }

    companion object {
        private val TAG = TasksActivity::class.simpleName
        private const val ID = "task id"
        fun Activity.launchTaskActivity(id: String) {
            startActivity(Intent(this, TaskActivity::class.java).apply {
                putExtra(ID, id)
            })
        }

        private val TaskActivity.id
            get() = intent.getStringExtra(ID) ?: throw Exception("Id is null")
    }
}
