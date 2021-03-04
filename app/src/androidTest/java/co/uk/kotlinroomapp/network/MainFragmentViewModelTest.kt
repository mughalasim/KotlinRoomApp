package co.uk.kotlinroomapp.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import co.uk.kotlinroomapp.data.api.ApiHelper
import co.uk.kotlinroomapp.data.local.DatabaseHelper
import co.uk.kotlinroomapp.data.local.entity.TaskEntity
import co.uk.kotlinroomapp.ui.MainFragmentViewModel
import co.uk.kotlinroomapp.utils.Resource
import co.uk.kotlinroomapp.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainFragmentViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiHelper: ApiHelper

    @Mock
    private lateinit var databaseHelper: DatabaseHelper

    @Mock
    private lateinit var listObserver: Observer<Resource<List<TaskEntity>>>

    @Before
    fun setUp() {
        // do something if required
    }

    @Test
    fun whenDatabaseIsEmpty_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Database is empty"
            doThrow(RuntimeException(errorMessage))
                .`when`(databaseHelper)
                .getRowCount()
            val viewModel = MainFragmentViewModel(apiHelper, databaseHelper)
            viewModel.tasks.observeForever(listObserver)
            verify(databaseHelper).getRowCount()
            verify(listObserver).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            viewModel.tasks.removeObserver(listObserver)
        }
    }

    @After
    fun tearDown() {
        // do something if required
    }

}