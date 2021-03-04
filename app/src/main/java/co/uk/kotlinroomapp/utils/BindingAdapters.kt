package co.uk.kotlinroomapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import co.uk.kotlinroomapp.R

@BindingAdapter("imageFromType")
fun bindImageFromType(view: ImageView, taskType: String?) {
    if (!taskType.isNullOrEmpty()) {
        view.setImageResource (when(taskType){
            "general" -> R.drawable.general
            "hydration" -> R.drawable.hydration
            "medication" -> R.drawable.medication
            "nutrition" -> R.drawable.nutrition
            else -> 0
        })
    }
}