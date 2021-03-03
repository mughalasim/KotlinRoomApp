package co.uk.kotlinroomapp.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import co.uk.kotlinroomapp.R

@BindingAdapter("imageFromType")
fun bindImageFromType(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        view.setImageResource (when(imageUrl){
            "general" -> R.drawable.general
            "hydration" -> R.drawable.hydration
            "medication" -> R.drawable.medication
            "nutrition" -> R.drawable.nutrition
            else -> 0
        })
    }
}