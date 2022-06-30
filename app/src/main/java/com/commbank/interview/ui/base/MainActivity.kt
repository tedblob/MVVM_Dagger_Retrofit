package com.commbank.interview.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.commbank.interview.data.model.HeadlinesViewModel
import com.commbank.interview.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import com.commbank.interview.R

import androidx.test.espresso.IdlingResource
import com.commbank.interview.ui.common.SimpleIdlingResource


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private var mIdlingResource: SimpleIdlingResource? = null

    private val viewModel: HeadlinesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.res.observe(this, {
            mIdlingResource?.setIdleState(true)
        })
        setSupportActionBar(binding.mainToolbar)
    }

    @VisibleForTesting
    fun getIdlingResource(): IdlingResource? {
        if (mIdlingResource == null) {
            mIdlingResource = SimpleIdlingResource()
        }

        mIdlingResource?.setIdleState(false)
        return mIdlingResource
    }

}