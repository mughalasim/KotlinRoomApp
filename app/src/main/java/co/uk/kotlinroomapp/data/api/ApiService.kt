package co.uk.kotlinroomapp.data.api

import co.uk.kotlinroomapp.data.local.entity.TaskEntity
import retrofit2.http.GET

interface ApiService {

    @GET("tasks.json")
    suspend fun getTasks(): List<TaskEntity>

}