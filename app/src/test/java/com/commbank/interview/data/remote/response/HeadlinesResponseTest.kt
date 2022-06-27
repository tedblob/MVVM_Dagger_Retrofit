package com.commbank.interview.data.remote.response

import com.google.gson.Gson
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors

class HeadlinesResponseTest {

    @Test
    fun getResponseTest() {
        val input = javaClass.classLoader?.getResourceAsStream("news_response.json")
        val output = BufferedReader(InputStreamReader(input))
            .lines().collect(Collectors.joining("\n"))
        val headlinesResponse = Gson().fromJson(output, HeadlinesResponse::class.java)
        assertEquals(69, headlinesResponse.totalResults)
    }

    @Test
    fun getStatusTest() {
        val input = javaClass.classLoader?.getResourceAsStream("news_response.json")
        val output = BufferedReader(InputStreamReader(input))
            .lines().collect(Collectors.joining("\n"))
        val headlinesResponse = Gson().fromJson(output, HeadlinesResponse::class.java)
        assertEquals("ok", headlinesResponse?.status)
    }

    @Test
    fun getListTest() {
        val input = javaClass.classLoader?.getResourceAsStream("news_response.json")
        val output = BufferedReader(InputStreamReader(input))
            .lines().collect(Collectors.joining("\n"))
        val headlinesResponse = Gson().fromJson(output, HeadlinesResponse::class.java)
        assertEquals(20, headlinesResponse?.headlines?.size)
    }

    @Test
    fun getHeadlinesTest() {
        val input = javaClass.classLoader?.getResourceAsStream("news_response.json")
        val output = BufferedReader(InputStreamReader(input))
            .lines().collect(Collectors.joining("\n"))
        val headlinesResponse = Gson().fromJson(output, HeadlinesResponse::class.java)
        val headlines = headlinesResponse.headlines?.get(0);
        assertEquals("Henry Khederian", headlines?.author)
    }

    @Test
    fun getHeadlinesSourceTest() {
        val input = javaClass.classLoader?.getResourceAsStream("news_response.json")
        val output = BufferedReader(InputStreamReader(input))
            .lines().collect(Collectors.joining("\n"))
        val headlinesResponse = Gson().fromJson(output, HeadlinesResponse::class.java)
        val headlines = headlinesResponse.headlines?.get(0);
        assertEquals("Benzinga", headlines?.source?.name)
    }

}