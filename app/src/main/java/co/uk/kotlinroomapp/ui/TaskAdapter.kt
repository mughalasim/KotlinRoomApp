package co.uk.kotlinroomapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.uk.kotlinroomapp.data.local.entity.TaskEntity
import co.uk.kotlinroomapp.databinding.ListItemTaskBinding

/**
 * Adapter for the [RecyclerView] in [MainFragment].
 */
class TaskAdapter : ListAdapter<TaskEntity, RecyclerView.ViewHolder>(TaskDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val task = getItem(position)
        (holder as TaskViewHolder).bind(task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TaskViewHolder(ListItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    class TaskViewHolder(private val binding: ListItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TaskEntity) {
            binding.apply {
                task = item
                executePendingBindings()
            }
        }
    }
}

private class TaskDiffCallback : DiffUtil.ItemCallback<TaskEntity>() {

    override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
        return oldItem == newItem
    }
}