package com.commbank.interview.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import com.commbank.interview.R
import com.commbank.interview.data.model.HeadlinesViewModel
import com.commbank.interview.databinding.FragmentHeadlinesListBinding
import androidx.recyclerview.widget.DividerItemDecoration


class HeadlinesListFragment(val factory: (() -> ViewModelProvider.Factory)? = null) : Fragment() {
    val viewModel: HeadlinesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentHeadlinesListBinding = DataBindingUtil.inflate<FragmentHeadlinesListBinding>(inflater, R.layout.fragment_headlines_list, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val layoutManager = LinearLayoutManager(this.context)
        val dividerItemDecoration = DividerItemDecoration(
            this.context,
            layoutManager.orientation
        )
        binding.headlinesRecyclerView.addItemDecoration(dividerItemDecoration)
        binding.headlinesRecyclerView.layoutManager = layoutManager

        return binding.root
    }
}