package com.example.crypto.repository


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.crypto.model.Data
import com.example.crypto.model.enums.SortType
import com.example.crypto.network.ApiClient
import com.example.crypto.network.ApiService


class CryptoRepository() : PagingSource<Int, Data>() {
    private val apiService = ApiClient.getClient().create(ApiService::class.java)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = apiService.getCryptoList(
                currentLoadingPageKey.toString(),
                "20",
                SortType.MARKET_CAP.sort
            )
            val responseData = mutableListOf<Data>()
            val data = response.body()?.data ?: emptyList()
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}