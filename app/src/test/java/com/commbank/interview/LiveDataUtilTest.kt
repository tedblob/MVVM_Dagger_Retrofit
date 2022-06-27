package com.commbank.interview

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.resetMain
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<T>.getOrAwaitValue(count: Int = 1) : T {
    var data: T? = null
    val countDownLatch = CountDownLatch(count)

    val observer= object: Observer<T> {
        override fun onChanged(t: T?) {
            data = t
            this@getOrAwaitValue.removeObserver(this)
            countDownLatch.countDown()
        }
    }

    this.observeForever(observer)
    try {
        if (!countDownLatch.await(5000, TimeUnit.SECONDS))  {
            throw TimeoutException("Live Data timeout expired!")
        }
    } finally {
        this.removeObserver(observer)
    }

    return data as T
}