package com.example.crypto.repository

import com.example.crypto.network.ApiClient
import com.example.crypto.network.ApiService

class DetailRepository {

    private val apiService = ApiClient.getClient().create(ApiService::class.java)

    suspend fun load(id: Int) = apiService.getCryptoInfoDetail(id.toString())

}

