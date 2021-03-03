package co.uk.kotlinroomapp.data.local

import co.uk.kotlinroomapp.data.local.entity.TaskEntity

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getTasks(): List<TaskEntity> = appDatabase.taskDao().getTasks()

    override suspend fun getRowCount() = appDatabase.taskDao().getRowCount()

    override suspend fun getFilteredTaskList(taskTypes: List<String>) = appDatabase.taskDao().getFilteredTaskList(taskTypes)

    override suspend fun insertAll(tasks: List<TaskEntity>) = appDatabase.taskDao().insertAll(tasks)

}