package com.enigma.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.agent.item_task.view.*

class TasksAdapter(private val onItemClick: (id: String) -> Unit) :
    RecyclerView.Adapter<BaseViewHolder>() {

    private val tasks: MutableList<TaskEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TaskViewHolder(parent)

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is TaskViewHolder -> holder.bindTask(tasks[position], onItemClick)
        }
    }

    fun setTasks(orders: List<TaskEntity>) {
        tasks.clear()
        tasks.addAll(orders)
        notifyDataSetChanged()
    }
}

sealed class BaseViewHolder(parent: ViewGroup, @LayoutRes layoutId: Int) :
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

class TaskViewHolder(parent: ViewGroup) : BaseViewHolder(parent, R.layout.item_task) {
    fun bindTask(
        task: TaskEntity,
        onItemClick: (id: String) -> Unit
    ) {
        itemView.apply {
            tag = task.id
            txt_item.text = task.item
            txt_status.text = task.status.name
            setOnClickListener { v -> onItemClick(v.tag as String) }
        }
    }
}