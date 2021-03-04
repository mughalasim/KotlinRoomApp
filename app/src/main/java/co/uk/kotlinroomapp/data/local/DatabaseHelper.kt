package co.uk.kotlinroomapp.data.local

import androidx.lifecycle.LiveData
import co.uk.kotlinroomapp.data.local.entity.TaskEntity

interface DatabaseHelper {

    suspend fun getTasks(): List<TaskEntity>

    suspend fun getRowCount(): Int

    suspend fun getFilteredTaskList(taskTypes: List<String>): List<TaskEntity>

    suspend fun insertAll(tasks: List<TaskEntity>)

}