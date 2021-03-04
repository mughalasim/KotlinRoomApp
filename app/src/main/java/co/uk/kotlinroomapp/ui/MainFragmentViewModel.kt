package co.uk.kotlinroomapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.uk.kotlinroomapp.data.api.ApiHelper
import co.uk.kotlinroomapp.data.local.DatabaseHelper
import co.uk.kotlinroomapp.data.local.entity.TaskEntity
import co.uk.kotlinroomapp.utils.Resource
import kotlinx.coroutines.launch

/**
 * The [ViewModel] for fetching a list of [TaskEntity]s.
 */
class MainFragmentViewModel internal constructor(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
) : ViewModel() {

    private val TAG = "ViewModel"
    private val _snackbar = MutableLiveData<String?>()
    private val _spinner = MutableLiveData(false)
    val spinner: LiveData<Boolean> get() = _spinner
    val snackbar: LiveData<String?> get() = _snackbar
    val isConnected = MutableLiveData(false) // Is assuming offline first
    var localDataCount: Int = 0 // There is no data in the local store to begin with so attempt to fetch from server

    val tasks = MutableLiveData<Resource<List<TaskEntity>>>()
    private var taskTypeList: ArrayList<String> = arrayListOf()


    // Clear the snack bar immediately after its shown
    fun onSnackBarShown() {
        _snackbar.value = null
    }

    fun setFilterOption(taskType: String, isAdded: Boolean) {
        if (isAdded) {
            taskTypeList.add(taskType)
        } else {
            taskTypeList.remove(taskType)
        }
        Log.d(TAG, "List of applied filters $taskTypeList")
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            tasks.postValue(Resource.loading(null))
            _spinner.value = true
            try {
                tryUpdateRecentTasksCache(taskTypeList)
            } catch (e: Exception) {
                tasks.postValue(Resource.error(e.toString(), null))
                _snackbar.value = e.toString()
            }
        }
    }

    private suspend fun tryUpdateRecentTasksCache(taskTypes: List<String>) {
        /*
        First check if there is no local storage and there is internet available then start
        fetching from the server..
        Possible idea - If the individual items in the list were updated, have a state property
        with each item that then returns a count of how many times need to be updated from the server
        */
        if (localDataCount == 0 && isConnected.value == true) {
            dbHelper.insertAll(apiHelper.getTasks())
            tasks.postValue(Resource.success(dbHelper.getTasks()))
            updateLocalDataCount()
        }

        // If a filter has been applied then use the local storage to sort it
        if (taskTypes.isEmpty()) {
            tasks.postValue(Resource.success(dbHelper.getTasks()))
        } else {
            tasks.postValue(Resource.success(dbHelper.getFilteredTaskList(taskTypes)))
        }
        _spinner.value = false
    }

    private suspend fun updateLocalDataCount(){
        localDataCount = dbHelper.getRowCount()
        Log.d(TAG, "Local Database item count: $localDataCount")
    }
}