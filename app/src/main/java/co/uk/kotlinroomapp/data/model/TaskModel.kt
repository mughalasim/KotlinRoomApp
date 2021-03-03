package co.uk.kotlinroomapp.data.model

import com.google.gson.annotations.SerializedName

data class TaskModel(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("type")
    val type: String = ""
)