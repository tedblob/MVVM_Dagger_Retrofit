package com.commbank.interview.ui.base


import android.app.Activity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.commbank.interview.R
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.IdlingRegistry

import androidx.test.core.app.ActivityScenario

import org.junit.Before

import androidx.test.espresso.IdlingResource
import com.commbank.interview.util.Util
import org.junit.After


@LargeTest
@RunWith(AndroidJUnit4::class)
class HeadlinesListFragmentTest {

    @Before
    fun registerIdlingResource() {
        val activityScenario: ActivityScenario<*> =
            ActivityScenario.launch(MainActivity::class.java)

        activityScenario.onActivity { activity ->
            mIdlingResource = (activity as MainActivity).getIdlingResource()
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
    fun headlinesListFragment() {

        val textView = onView(
            allOf(
                Util.first(
                    allOf(
                        withText("CBANews"),
                        withParent(
                            allOf(
                                withId(R.id.mainToolbar),
                                withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                            )
                        ),
                        isDisplayed()
                    )
                )
            )
        )
        textView.check(matches(withText("CBANews")))

        val imageView = onView(
            allOf(
                Util.first(
                    withId(R.id.headlinesImageView)
                )
            )
        )
        imageView.check(matches(isDisplayed()))

        val textView3 = onView(
            allOf(
                Util.first(
                    withId(R.id.headlinesTime)
                )
            )
        )
        textView3.check(matches(isDisplayed()))

    }
}
