package com.commbank.interview

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.commbank.interview.ui.base.HeadlinesListFragment
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationControllerTest {

    @Test
    fun testNavigationToInGameScreen() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        val headlinesListFragment = launchFragmentInContainer<HeadlinesListFragment>()

        headlinesListFragment.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_type)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(ViewMatchers.withId(R.id.headlinesChildContainer)).perform(ViewActions.click())
        assertThat("Mismatch navigation", navController.currentDestination?.id == R.id.headlinesDetailFragment)
    }
}