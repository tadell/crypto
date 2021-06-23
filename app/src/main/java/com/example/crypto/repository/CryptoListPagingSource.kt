package com.example.crypto.repository

import androidx.paging.PagingSource
import com.example.crypto.model.Data
import com.example.crypto.network.ApiClient
import com.example.crypto.network.ApiService


class CryptoListPagingSource(
    private val sortText: String,
    private val sortDesc: String,
    private val cryptoText: String,
    private val tagText: String
) : PagingSource<Int, Data>() {
    private var responseData = mutableListOf<Data>()
    private val apiService = ApiClient.getClient().create(ApiService::class.java)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = apiService.getCryptoList(
                currentLoadingPageKey.toString(),
                "20",
                sortText,
                sortDesc,
                cryptoText,
                tagText
            )
            val prevKey =
                if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            val nextKey =
                if (response.body()?.data?.isEmpty() == true) null else currentLoadingPageKey.plus(1)

            when {
                response.isSuccessful -> {
                    responseData = mutableListOf()
                    val data = response.body()?.data ?: emptyList()
                    responseData.addAll(data)
                }

            }
            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = nextKey)

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}