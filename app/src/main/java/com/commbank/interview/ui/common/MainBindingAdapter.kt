package com.commbank.interview.ui.common

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.commbank.interview.data.remote.response.Headlines
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.commbank.interview.R
import com.commbank.interview.ui.base.HeadlinesDetailFragment
import com.commbank.interview.ui.base.HeadlinesListFragment
import com.commbank.interview.ui.base.HeadlinesListFragmentDirections
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("headlines")
fun bindItemViewModels(recyclerView: RecyclerView, headlines: List<Headlines>?) {
    val adapter = getOrCreateAdapter(recyclerView)
    headlines?.let {
        adapter.updateItems(it)
    }

}

@BindingAdapter("bindPublishDate")
fun bindPublishDate(textView: TextView, date: String?) {
    val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val output = SimpleDateFormat("dd-MM-yyyy HH:mm aa", Locale.getDefault())

    try {
        date?.let {
            input.parse(date)?.let {
                textView.text = output.format(it)
            }
        }

    } catch (e: ParseException) {
        e.printStackTrace()
    }
}

@BindingAdapter("bindSrc")
fun bindHeadlinesImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        view.visibility = View.VISIBLE
        Glide.with(view.context).load(url)
            .centerCrop()
            .into(view)
    }
}

@BindingAdapter("bindSrcDetail")
fun bindSrcDetail(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        view.visibility = View.VISIBLE
        Glide.with(view.context).load(url)
            .centerCrop()
            .into(view)
    }
}

private fun getOrCreateAdapter(recyclerView: RecyclerView): HeadlinesRecyclerViewAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is HeadlinesRecyclerViewAdapter) {
        recyclerView.adapter as HeadlinesRecyclerViewAdapter
    } else {
        val bindableRecyclerAdapter = HeadlinesRecyclerViewAdapter(onClick = {
            it.let {
                val action = HeadlinesListFragmentDirections.loadFragment(it)
                Navigation.findNavController(recyclerView).navigate(action)
            }
        })

        recyclerView.adapter = bindableRecyclerAdapter
        bindableRecyclerAdapter
    }
}