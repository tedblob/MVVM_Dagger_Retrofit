package com.commbank.interview.util

import com.commbank.interview.BuildConfig
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors

class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url().uri().toString()
            val responseString = when {
                uri.contains("top-headlines") -> getTopHeadlines()
                else -> ""
            }

            return chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    ResponseBody.create(
                        MediaType.parse("application/json"),
                        responseString.toByteArray()
                    )
                )
                .addHeader("content-type", "application/json")
                .build()
        } else {
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and " +
                        "bound to be used only with DEBUG mode"
            )
        }
    }

    private fun getTopHeadlines(): String {
        val input = javaClass.classLoader?.getResourceAsStream("news_response.json")
        return BufferedReader(InputStreamReader(input))
            .lines().collect(Collectors.joining("\n"))
    }
}