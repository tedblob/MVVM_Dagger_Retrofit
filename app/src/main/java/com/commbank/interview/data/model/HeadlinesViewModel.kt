package com.commbank.interview.data.model

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commbank.interview.BuildConfig
import com.commbank.interview.data.remote.response.HeadlinesResponse
import com.commbank.interview.data.repository.HeadlinesDataSource
import com.commbank.interview.util.AppConstants
import com.commbank.interview.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class HeadlinesViewModel @Inject constructor(private val headlinesDataSource: HeadlinesDataSource): ViewModel() {

    private val _res = MutableLiveData<Resource<HeadlinesResponse>>()

    val res : LiveData<Resource<HeadlinesResponse>>
        get() = _res

    init {
        getHeadlines()
    }

    val isLoading = ObservableBoolean()

    fun onRefresh() {
        isLoading.set(true)
        getHeadlines()
    }

    fun getHeadlines()  = viewModelScope.launch {
        // TODO update view
        _res.postValue(Resource.loading(null))

        val map = HashMap<String, String>()
        map["country"] = AppConstants.COUNTRY
        map["category"] = AppConstants.BUSINESS
        map["apiKey"] = BuildConfig.API_KEY

        headlinesDataSource.getAllUSHeadlines(map).let { resp ->
            _res.postValue(Resource.success(resp))
            isLoading.set(false)
        }

    }
}
