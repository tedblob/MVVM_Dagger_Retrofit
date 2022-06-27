package com.commbank.interview.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.commbank.interview.R
import com.commbank.interview.data.model.HeadlinesViewModel
import com.commbank.interview.databinding.FragmentHeadlinesListBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration




class HeadlinesListFragment : Fragment() {
    private val viewModel: HeadlinesViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentHeadlinesListBinding = DataBindingUtil.inflate<FragmentHeadlinesListBinding>(inflater, R.layout.fragment_headlines_list, container, false)

        val rootView: SwipeRefreshLayout = binding.swipeRefreshLayout
        val recyclerview: RecyclerView = binding.headlinesRecyclerView
        val layoutManager = LinearLayoutManager(this.activity)
        val dividerItemDecoration = DividerItemDecoration(
            recyclerview.context,
            layoutManager.orientation
        )
        recyclerview.addItemDecoration(dividerItemDecoration)

        recyclerview.layoutManager = layoutManager
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        rootView.setOnRefreshListener {
            viewModel.getHeadlines()
        }

        return binding.root
    }
}