package com.commbank.interview.data.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.commbank.interview.util.CoroutineTestRule
import com.commbank.interview.util.InstantExecutorExtension
import com.commbank.interview.util.MockInterceptor
import com.commbank.interview.data.repository.HeadlinesDataSource
import com.commbank.interview.data.service.ApiService
import com.commbank.interview.util.getOrAwaitValue
import com.commbank.interview.util.AppConstants
import com.commbank.interview.util.Status
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import okhttp3.OkHttpClient
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExtendWith(InstantExecutorExtension::class)
@ExperimentalCoroutinesApi
class HeadlinesViewModelTest {

    private lateinit var viewModel: HeadlinesViewModel
    private val dispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        val myRepository = HeadlinesDataSource(createApiService())
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
    fun `Check response data null`() = runTest {

        launch(Dispatchers.Main) {
            viewModel.getHeadlines()
            val value = viewModel.res.getOrAwaitValue(2)
            println(value)
            assertThat("Value is null", value.data != null)
        }
    }

    @Test
    fun `Check response status success`() = runTest {

        launch(Dispatchers.Main) {
            viewModel.getHeadlines()
            val value = viewModel.res.getOrAwaitValue(2)
            println(value)
            assertThat("Status not success", value.status == Status.SUCCESS)
        }
    }


    @Test
    fun `Check response count as expected`() = runTest {

        launch(Dispatchers.Main) {
            viewModel.getHeadlines()
            val value = viewModel.res.getOrAwaitValue(2)
            println(value)
            val headlines = value.data?.headlines
            assertThat("Headlines is empty", headlines != null &&
                    headlines.isNotEmpty()
            )
        }
    }
}