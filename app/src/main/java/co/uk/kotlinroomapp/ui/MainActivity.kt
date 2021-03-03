package co.uk.kotlinroomapp.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import co.uk.kotlinroomapp.R
import co.uk.kotlinroomapp.utils.InternetUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        InternetUtil.observe(this, { isConnected ->
            if (isConnected){
               findViewById<TextView>(R.id.text_view_offline).visibility = View.GONE
            } else {
                findViewById<TextView>(R.id.text_view_offline).visibility = View.VISIBLE
            }
        })
    }
}