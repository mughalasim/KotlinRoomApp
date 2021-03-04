package co.uk.kotlinroomapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import co.uk.kotlinroomapp.data.api.ApiHelperImpl
import co.uk.kotlinroomapp.data.api.RetrofitBuilder
import co.uk.kotlinroomapp.data.local.DatabaseBuilder
import co.uk.kotlinroomapp.data.local.DatabaseHelperImpl
import co.uk.kotlinroomapp.databinding.FragmentMainBinding
import co.uk.kotlinroomapp.utils.InternetUtil
import co.uk.kotlinroomapp.utils.Status
import co.uk.kotlinroomapp.utils.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {
    private val inActive = 0.2f
    private val active = 1.0f
    private lateinit var viewModel: MainFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        context ?: return binding.root

        // Set up view model using the factory to pass in the retrofit instance and database instance
        viewModel =
            ViewModelProviders.of(
                this, ViewModelFactory(
                    ApiHelperImpl(RetrofitBuilder.apiService),
                    DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext()))
                )
            ).get(MainFragmentViewModel::class.java)

        // show the spinner when [spinner] is true
        viewModel.spinner.observe(viewLifecycleOwner) { show ->
            binding.spinner.visibility = if (show) View.VISIBLE else View.GONE
        }

        // Show a snackbar whenever the [snackbar] is updated a non-null value
        viewModel.snackbar.observe(viewLifecycleOwner) { text ->
            text?.let {
                Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).show()
                viewModel.onSnackBarShown()
            }
        }

        binding.imgGeneral.alpha = inActive
        binding.imgMedication.alpha = inActive
        binding.imgHydration.alpha = inActive
        binding.imgNutrition.alpha = inActive

        binding.imgGeneral.setOnClickListener {
            setFilterActive(binding.imgGeneral, "general")
        }

        binding.imgMedication.setOnClickListener {
            setFilterActive(binding.imgMedication, "medication")
        }

        binding.imgHydration.setOnClickListener {
            setFilterActive(binding.imgHydration, "hydration")
        }

        binding.imgNutrition.setOnClickListener {
            setFilterActive(binding.imgNutrition, "nutrition")
        }

        val adapter = TaskAdapter()
        binding.taskList.adapter = adapter

        viewModel.tasks.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    // Make some more loading animations here
                    Log.d("MainFragment", "Loading data")
                }
                Status.SUCCESS -> {
                    Log.d("MainFragment", "Data updated ${it.data?.size}")
                    adapter.submitList(it.data)
                }
                Status.ERROR -> {
                    Log.d("MainFragment", "Error ${it.message}")
                    // Do some more error handling here
                }
            }
        })

        // Need to have this observer here as it will be managed by this fragments lifecycle
        InternetUtil.observe(this, { isConnected ->
            viewModel.isConnected.postValue(isConnected)
            viewModel.fetchData()
        })

        return binding.root
    }

    private fun setFilterActive(img: ImageView, taskType: String) {
        img.alpha = if (img.alpha == inActive) active else inActive
        viewModel.setFilterOption(taskType, img.alpha == active)
    }

}