package com.commbank.interview.ui.common

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.commbank.interview.data.remote.response.Headlines
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("headlines")
fun bindItemViewModels(recyclerView: RecyclerView, headlines: List<Headlines>?) {

    headlines?.let {
        if (recyclerView.adapter != null && recyclerView.adapter is HeadlinesRecyclerViewAdapter) {
            val adapter = recyclerView.adapter as HeadlinesRecyclerViewAdapter
            adapter.updateItems(headlines)
        } else {
            val adapter = HeadlinesRecyclerViewAdapter(headlines)
            recyclerView.adapter = adapter
            adapter
        }
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

@BindingAdapter("android:src")
fun bindHeadlinesImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        view.visibility = View.VISIBLE
        Glide.with(view.context).load(url)
            .centerCrop()
            .into(view)
    }
}