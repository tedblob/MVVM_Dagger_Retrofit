package com.commbank.interview.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.commbank.interview.data.model.HeadlinesViewModel
import com.commbank.interview.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.widget.Toolbar
import com.commbank.interview.R


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: HeadlinesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setSupportActionBar(binding.mainToolbar)
    }

}