package com.commbank.interview.ui.base

import androidx.test.filters.LargeTest
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingPolicies
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import com.commbank.interview.R
import com.commbank.interview.util.RecyclerViewActionOnChild

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import java.util.concurrent.TimeUnit

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Before
    fun registerIdlingResource() {
        val activityScenario: ActivityScenario<*> =
            ActivityScenario.launch(MainActivity::class.java)

        activityScenario.onActivity { activity ->
            mIdlingResource = (activity as MainActivity).getIdlingResource()
            IdlingPolicies.setMasterPolicyTimeout(3, TimeUnit.MINUTES)
            IdlingPolicies.setIdlingResourceTimeout(3, TimeUnit.MINUTES)
            IdlingRegistry.getInstance().register(mIdlingResource)
        }
    }

    private var mIdlingResource: IdlingResource? = null

    @After
    fun unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource)
        }
    }

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val recyclerView = onView(
            allOf(
                withId(R.id.headlinesRecyclerView)
            )
        )
        recyclerView.perform(
            actionOnItemAtPosition<ViewHolder>(0,
                RecyclerViewActionOnChild.clickChildViewWithId(R.id.headlinesChildContainer)));

        val actionMenuItemView = onView(
            allOf(
                withId(R.id.action_share), withContentDescription("Share"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.mainToolbar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
