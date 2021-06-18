package com.example.crypto.network

import com.example.crypto.model.CryptoList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    //https://pro.coinmarketcap.com/api/v1#section/Quick-Start-Guide
    @Headers("X-CMC_PRO_API_KEY: aec338c7-932e-464d-be06-a062bf0b4e87")
    @GET("v1/cryptocurrency/listings/latest?")
    suspend fun getCryptoList(@Query("start") start: String  ,@Query("limit") limit: String = "20" )
    : Response<CryptoList>
}