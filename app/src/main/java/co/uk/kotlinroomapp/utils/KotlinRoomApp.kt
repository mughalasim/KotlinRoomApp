package co.uk.kotlinroomapp.utils

import android.app.Application

class KotlinRoomApp : Application() {

    override fun onCreate() {
        super.onCreate()
        InternetUtil.init(this)
    }
}