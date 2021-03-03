package co.uk.kotlinroomapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import co.uk.kotlinroomapp.data.local.dao.TaskDao
import co.uk.kotlinroomapp.data.local.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

}