package com.commbank.interview.ui.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.commbank.interview.data.model.HeadlinesViewModel
import androidx.navigation.fragment.navArgs
import com.commbank.interview.databinding.FragmentHeadlinesDetailBinding
import android.R
import android.content.Intent
import android.view.*


class HeadlinesDetailFragment : Fragment() {
    private val viewModel: HeadlinesViewModel by activityViewModels()
    private val args: HeadlinesDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentHeadlinesDetailBinding = DataBindingUtil
            .inflate(inflater, com.commbank.interview.R.layout.fragment_headlines_detail, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.headline = args.headlines

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(com.commbank.interview.R.menu.app_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {

        com.commbank.interview.R.id.action_share -> {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, args.headlines.url)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

}