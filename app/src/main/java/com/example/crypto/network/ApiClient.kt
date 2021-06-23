package com.example.crypto.network

import com.example.crypto.network.ApiEndPoint.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        private val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Accept", "application/json")
                    .addHeader("X-CMC_PRO_API_KEY", "3069b872-06b0-40f1-bf44-5a3f7f73d796")
                chain.proceed(request.build())
            }
            .build()

        var retrofit: Retrofit? = null

        fun getClient(): Retrofit {
            when (retrofit) {
                null -> retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .client(okHttpClient)
                    .build()
            }
            return retrofit as Retrofit
        }
    }

    private fun OkHttpClient.Builder.setApiKey() = addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .header("Accept", "application/json")
            .addHeader("X-CMC_PRO_API_KEY", "3069b872-06b0-40f1-bf44-5a3f7f73d796")
        chain.proceed(request.build())
    }
}