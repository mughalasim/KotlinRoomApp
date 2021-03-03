package co.uk.kotlinroomapp.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import co.uk.kotlinroomapp.utils.CustomMatchers.Companion.withText
import co.uk.kotlinroomapp.R
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest : TestCase() {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    // Test that the offline banner is a Text View that has text in it
    @Test
    fun checkTextReadsOfflineText() {
        onView(withId(R.id.text_view_offline))
            .check(matches(withText(activityRule.activity.resources.getString(R.string.txt_offline))))
    }
}

