package com.example.crypto.network

import com.example.crypto.model.CryptoList
import com.example.crypto.model.DetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //list fragment api for get cryptocurrencies
    @GET("v1/cryptocurrency/listings/latest?")
    suspend fun getCryptoList(
        @Query("start") start: String,
        @Query("limit") limit: String,
        @Query("sort") sort: String,
        @Query("sort_dir") sort_dir: String,
        @Query("cryptocurrency_type") cryptocurrency_type: String,
        @Query("tag") tag: String
    )
            : Response<CryptoList>


    //detail fragment api for getting details of each cryptocurrency by its id
    @GET("v1/cryptocurrency/info?")
    suspend fun getCryptoInfoDetail(@Query("id") id: String)
            : Response<DetailResponse>
}