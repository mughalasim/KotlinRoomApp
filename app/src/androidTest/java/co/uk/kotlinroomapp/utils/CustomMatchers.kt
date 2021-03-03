package co.uk.kotlinroomapp.utils

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

class CustomMatchers {
    companion object {
        fun withItemCount(count: Int): Matcher<View> {
            return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
                override fun matchesSafely(item: RecyclerView?): Boolean {
                    return item?.adapter?.itemCount == count
                }

                override fun describeTo(description: Description?) {
                    description?.appendText("RecyclerView with item count: $count")
                }
            }
        }

        fun withText(text: String): Matcher<View> {
            return object : BoundedMatcher<View, TextView>(TextView::class.java) {
                override fun matchesSafely(item: TextView?): Boolean {
                    return item?.text == text
                }

                override fun describeTo(description: Description?) {
                    description?.appendText("Text view has different text: $text")
                }
            }
        }
    }
}