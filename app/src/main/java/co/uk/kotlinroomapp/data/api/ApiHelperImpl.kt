package co.uk.kotlinroomapp.data.api

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun getTasks() = apiService.getTasks()

}