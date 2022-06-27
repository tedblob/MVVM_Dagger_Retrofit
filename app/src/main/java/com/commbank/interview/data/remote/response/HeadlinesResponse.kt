package com.commbank.interview.data.remote.response

import com.google.gson.annotations.SerializedName

data class HeadlinesResponse(val status: String?,
                             val totalResults: Int?,
                             @SerializedName("articles")
                             val headlines: List<Headlines>?)