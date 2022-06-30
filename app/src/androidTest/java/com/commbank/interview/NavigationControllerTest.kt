package com.commbank.interview

import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.*
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.commbank.interview.data.model.HeadlinesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import androidx.lifecycle.ViewModelProvider
import com.commbank.interview.data.repository.HeadlinesDataSource
import com.commbank.interview.data.service.ApiService
import com.commbank.interview.ui.base.HeadlinesListFragment
import com.commbank.interview.util.AppConstants
import com.commbank.interview.util.MockFragmentFactoryImpl
import com.commbank.interview.util.MockInterceptorClass
import okhttp3.OkHttpClient
import org.mockito.Mockito
import retrofit2.Retrofit
import org.koin.test.KoinTest
import retrofit2.converter.gson.GsonConverterFactory

import io.mockk.mockk
import io.mockk.every

@ExperimentalCoroutinesApi
class NavigationControllerTest : KoinTest {

    private lateinit var fragmentFactory : MockFragmentFactoryImpl

    @Before
    fun setup() {

        fragmentFactory = MockFragmentFactoryImpl()

        val myRepository = HeadlinesDataSource(createApiService())
        val viewModel = HeadlinesViewModel(myRepository)
        val viewModelFactory : ViewModelProvider.Factory = mockk()
        every { viewModelFactory.create(HeadlinesViewModel::class.java) } answers { viewModel }
        every { fragmentFactory.instantiate(any(), any()) } answers { HeadlinesListFragment{
                viewModelFactory
            }
        }
    }

    @Test
    fun testNavigationToDetailScreen() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        val scenario = launchFragmentInContainer<HeadlinesListFragment>(
            themeResId = R.style.Theme_CBANews,
            factory = fragmentFactory
        )

        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.onFragment {
            Mockito.`when`(it.viewModel.getHeadlines()).thenAnswer { _ -> Any() }
            navController.setGraph(R.navigation.nav_type)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(ViewMatchers.withId(R.id.headlinesChildContainer)).perform(ViewActions.click())

    }

    private fun createApiService(): ApiService {
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().addInterceptor(MockInterceptorClass()).build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AppConstants.BASE_URL)
            .build().create(ApiService::class.java)
    }
}

inline fun <reified VM : ViewModel> Fragment.activityViewModels(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = createViewModelLazy(VM::class, { requireActivity().viewModelStore },
    factoryProducer ?: { requireActivity().defaultViewModelProviderFactory })