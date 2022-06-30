package com.commbank.interview.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.commbank.interview.ui.base.HeadlinesListFragment
import com.commbank.interview.ui.base.HeadlinesListFragmentTest

open class MockFragmentFactoryImpl : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (loadFragmentClass(classLoader, className)) {
            HeadlinesListFragmentTest::class.java -> HeadlinesListFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}