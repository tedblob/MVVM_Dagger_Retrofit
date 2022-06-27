package com.commbank.interview.data.remote.response

import java.io.Serializable

data class Headlines(val source: Source?,
                     val author: String?,
                     val title: String?,
                     val description: String?,
                     val url: String?,
                     val urlToImage: String?,
                     val publishedAt: String?,
                     val content: String?) : Serializable
