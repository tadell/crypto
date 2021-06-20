package com.example.crypto.repository

import com.example.crypto.model.InfoData
import com.example.crypto.network.ApiClient
import com.example.crypto.network.ApiService

class DetailRepository {

    private val apiService = ApiClient.getClient().create(ApiService::class.java)

    suspend fun load(id: Int): MutableList<InfoData> {
        val response = apiService.getCryptoInfoDetail(id.toString())
        val responseData = mutableListOf<InfoData>()
        val data = response.body()?.data ?: emptyList()
        responseData.addAll(data)
        return responseData
    }
}

