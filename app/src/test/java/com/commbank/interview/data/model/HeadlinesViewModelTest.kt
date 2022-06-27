package com.commbank.interview.data.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.commbank.interview.CoroutineTestRule
import com.commbank.interview.InstantExecutorExtension
import com.commbank.interview.MockInterceptor
import com.commbank.interview.data.repository.MainRepository
import com.commbank.interview.data.service.ApiService
import com.commbank.interview.getOrAwaitValue
import com.commbank.interview.util.AppConstants
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import okhttp3.OkHttpClient
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.verifyBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExtendWith(InstantExecutorExtension::class)
@ExperimentalCoroutinesApi
internal class HeadlinesViewModelTest {

    private lateinit var viewModel: HeadlinesViewModel
    private val dispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        val myRepository = MainRepository(createApiService())
        viewModel = HeadlinesViewModel(myRepository)
    }

    private fun createApiService(): ApiService {
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().addInterceptor(MockInterceptor()).build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AppConstants.BASE_URL)
            .build().create(ApiService::class.java)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun isLoading() {
        viewModel.isLoading.set(true)
        assertThat("Not equal", viewModel.isLoading.get())
    }


    @Test
    fun onRefresh() = runTest {

        launch(Dispatchers.Main) {
            viewModel.onRefresh()
            assertThat("Not equal", viewModel.isLoading.get())
            // initial service returns LOADING so added waiting
            Thread.sleep(2000)
            val value = viewModel.res.getOrAwaitValue(1)
            println(value)
            assertThat("Value is null", value.data != null)

        }
    }

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun getHeadlines() = runTest {

        launch(Dispatchers.Main) {
            viewModel.getHeadlines()
            // initial service returns LOADING so added waiting
            Thread.sleep(2000)
            val value = viewModel.res.getOrAwaitValue(1)
            println(value)
            assertThat("Value is null", value.data != null)
        }
    }
}