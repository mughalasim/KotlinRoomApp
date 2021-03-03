package co.uk.kotlinroomapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.uk.kotlinroomapp.data.api.ApiHelper
import co.uk.kotlinroomapp.data.local.DatabaseHelper
import co.uk.kotlinroomapp.ui.MainFragmentViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val apiHelper: ApiHelper, private val dbHelper: DatabaseHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)) {
            return MainFragmentViewModel(apiHelper, dbHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}