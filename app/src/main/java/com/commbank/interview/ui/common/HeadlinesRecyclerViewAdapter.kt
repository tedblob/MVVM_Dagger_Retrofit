package com.commbank.interview.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import com.commbank.interview.BR
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.commbank.interview.R
import com.commbank.interview.data.remote.response.Headlines
import com.commbank.interview.databinding.ItemHeadlinesBinding
import com.commbank.interview.ui.presenters.HeadlinesListItemListener

class HeadlinesRecyclerViewAdapter(private var headlines: List<Headlines> = emptyList()) : RecyclerView.Adapter<HeadlinesViewHolder>() {

    private val mHeadlinesListItemListener = HeadlinesListItemListener()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlinesViewHolder {
        val binding: ItemHeadlinesBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_headlines,
            parent,
            false
        )

        binding.listener = mHeadlinesListItemListener
        return HeadlinesViewHolder(binding)
    }

    override fun getItemCount(): Int = headlines.size

    override fun onBindViewHolder(holder: HeadlinesViewHolder, position: Int) {
        holder.bind(headlines[position])
    }

    fun updateItems(headlines: List<Headlines>) {
        this.headlines = headlines
        notifyDataSetChanged()
    }
}

class HeadlinesViewHolder(private val binding: ItemHeadlinesBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(headline: Headlines) {
        binding.setVariable(BR.headline, headline)
    }
}