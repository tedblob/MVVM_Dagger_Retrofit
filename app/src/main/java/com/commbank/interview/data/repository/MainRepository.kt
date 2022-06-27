package com.commbank.interview.data.repository

import com.commbank.interview.data.remote.response.HeadlinesResponse
import com.commbank.interview.data.service.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getAllUSHeadlines(queryParams: Map<String, String>) : HeadlinesResponse = apiService.getAllUSHeadlines(queryParams)
}