package co.uk.kotlinroomapp.data.api

import co.uk.kotlinroomapp.data.local.entity.TaskEntity

interface ApiHelper {

    suspend fun getTasks(): List<TaskEntity>

}