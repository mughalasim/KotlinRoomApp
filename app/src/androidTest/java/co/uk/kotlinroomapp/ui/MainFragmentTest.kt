package co.uk.kotlinroomapp.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import co.uk.kotlinroomapp.utils.CustomMatchers.Companion.withItemCount
import co.uk.kotlinroomapp.R
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentTest : TestCase() {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    // Test that the recyclerview has the default number of items in the list = 6
    @Test
    fun checkItemListCount() {
        onView(withId(R.id.task_list))
            .check(matches(withItemCount(6)))
    }
}

