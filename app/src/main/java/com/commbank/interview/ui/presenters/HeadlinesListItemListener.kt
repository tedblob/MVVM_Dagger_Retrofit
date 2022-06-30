package com.commbank.interview.ui.presenters

import android.view.View
import androidx.navigation.Navigation
import com.commbank.interview.data.remote.response.Headlines
import com.commbank.interview.ui.base.HeadlinesListFragmentDirections

class HeadlinesListItemListener {
    fun onClickItem(view: View, headlines: Headlines) {
        val action = HeadlinesListFragmentDirections.loadFragment(headlines)
        Navigation.findNavController(view).navigate(action)
    }
}