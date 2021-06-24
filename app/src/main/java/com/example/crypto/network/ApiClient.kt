package com.example.crypto.network

import com.example.crypto.helper.Constants.Companion.API_KEY
import com.example.crypto.helper.Constants.Companion.API_KEY_VALUE
import com.example.crypto.helper.Constants.Companion.HEADER_KEY
import com.example.crypto.helper.Constants.Companion.HEADER_KEY_VALUE
import com.example.crypto.helper.Constants.Companion.TIMEOUT_VALUE
import com.example.crypto.network.ApiEndPoint.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        //header
        private fun OkHttpClient.Builder.setApiKey() = addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header(HEADER_KEY, HEADER_KEY_VALUE)
                .addHeader(API_KEY, API_KEY_VALUE)
            chain.proceed(request.build())
        }

        private val okHttpClient = OkHttpClient.Builder()
            .setApiKey()
            .connectTimeout(TIMEOUT_VALUE, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_VALUE, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_VALUE, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
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


}