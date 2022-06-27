package com.commbank.interview.injection.module

import androidx.lifecycle.viewModelScope
import com.commbank.interview.BuildConfig
import com.commbank.interview.data.model.HeadlinesViewModel
import com.commbank.interview.data.service.ApiService
import com.commbank.interview.util.APIConstants
import com.commbank.interview.util.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideCoroutineScope(viewModel: HeadlinesViewModel): CoroutineScope {
        return viewModel.viewModelScope
    }

    @Provides
    fun provideBaseUrl() = AppConstants.BASE_URL

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @get:Singleton
    @get:Provides
    val okHttpClient: OkHttpClient
        get() {
            val okHttpClientBuilder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG){
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                okHttpClientBuilder.addInterceptor(loggingInterceptor)
            }else{
                OkHttpClient
                    .Builder()
                    .build()
            }
            okHttpClientBuilder.connectTimeout(APIConstants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            okHttpClientBuilder.readTimeout(APIConstants.READ_TIMEOUT, TimeUnit.MILLISECONDS)
            return okHttpClientBuilder.build()
        }
}