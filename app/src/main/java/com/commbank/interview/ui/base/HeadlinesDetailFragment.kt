package com.commbank.interview.ui.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.commbank.interview.databinding.FragmentHeadlinesDetailBinding
import com.commbank.interview.R
import android.content.Intent
import android.view.*


class HeadlinesDetailFragment : Fragment() {
    private val args: HeadlinesDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentHeadlinesDetailBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_headlines_detail, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.headline = args.headlines

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {

        R.id.action_share -> {
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
            super.onOptionsItemSelected(item)
        }
    }

}