package com.commbank.interview.data.service

import com.commbank.interview.data.remote.response.HeadlinesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("top-headlines")
    suspend fun getAllUSHeadlines(@QueryMap map: Map<String, String>): HeadlinesResponse
}