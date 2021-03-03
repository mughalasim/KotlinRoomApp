package co.uk.kotlinroomapp.ui

import android.util.Log
import androidx.lifecycle.*
import co.uk.kotlinroomapp.data.api.ApiHelper
import co.uk.kotlinroomapp.data.local.DatabaseHelper
import co.uk.kotlinroomapp.data.local.entity.TaskEntity
import co.uk.kotlinroomapp.utils.InternetUtil
import co.uk.kotlinroomapp.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * The [ViewModel] for fetching a list of [TaskEntity]s.
 */
class MainFragmentViewModel internal constructor(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
    ) : ViewModel() {

    private val _snackbar = MutableLiveData<String?>()
    private val _spinner = MutableLiveData(false)
    val spinner: LiveData<Boolean> get() = _spinner
    val snackbar: LiveData<String?> get() = _snackbar

    fun onSnackbarShown() {
        _snackbar.value = null
    }

    private var taskTypeList: ArrayList<String> = arrayListOf()

    val tasks = MutableLiveData<Resource<List<TaskEntity>>>()

    fun setFilterOption(taskType: String, isAdded: Boolean) {
        if (isAdded) {
            taskTypeList.add(taskType)
        } else {
            taskTypeList.remove(taskType)
        }
        Log.d("List of applied filters", taskTypeList.toString())
        fetchData()
    }

    fun reFetchData(isConnected: Boolean) {
        if (isConnected) {
           fetchData()
        }
    }

    private fun fetchData(){
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
        when {
            // Obviously this is not ideal, we would check the state of the data if it has changed and mark it as such
            // ie dirty state, needs to be uploaded or pulled down etc
            dbHelper.getRowCount() == 0 -> {
                fetchTasksFromNetwork()
            }
            taskTypes.isEmpty() -> {
                tasks.postValue(Resource.success(dbHelper.getTasks()))
            }
            else -> {
                fetchTaskFromDB(taskTypes)
            }
        }
        _spinner.value = false
    }

    private suspend fun fetchTasksFromNetwork() {
        dbHelper.insertAll(apiHelper.getTasks())
        tasks.postValue(Resource.success(dbHelper.getTasks()))
    }

    private suspend fun fetchTaskFromDB(taskTypes: List<String>) {
        tasks.postValue(Resource.success(dbHelper.getFilteredTaskList(taskTypes)))
    }
}