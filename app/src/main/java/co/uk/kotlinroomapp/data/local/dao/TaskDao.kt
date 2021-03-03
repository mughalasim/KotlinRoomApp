package co.uk.kotlinroomapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.uk.kotlinroomapp.data.local.entity.TaskEntity

@Dao
interface TaskDao {
    @Query("SELECT COUNT(name) FROM tasks")
    suspend fun getRowCount(): Int

    @Query("SELECT * FROM tasks ORDER BY name")
    suspend fun getTasks(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE type IN (:taskTypes) ORDER BY name")
    suspend fun getFilteredTaskList(taskTypes: List<String>): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tasks: List<TaskEntity>)
}
