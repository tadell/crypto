package com.example.crypto.repository

import android.content.Context
import android.widget.Toast
import androidx.paging.PagingSource
import com.example.crypto.model.Data
import com.example.crypto.network.ApiClient
import com.example.crypto.network.ApiService


class CryptoListPagingSource(
    private val context: Context,
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

                response.code() == 401 -> {
                    Toast.makeText(context, "Unauthorized", Toast.LENGTH_LONG)
                        .show()
                }
                response.code() == 402  -> {
                    Toast.makeText(context, "Unauthorized", Toast.LENGTH_LONG)
                        .show()
                }
                response.code() == 403  -> {
                    Toast.makeText(context, "Forbidden", Toast.LENGTH_LONG)
                        .show()
                }
                response.code() == 429  -> {
                    Toast.makeText(context, "Too Many Requests", Toast.LENGTH_LONG)
                        .show()
                }
                response.code() == 500  -> {
                    Toast.makeText(context, "Internal Server Error", Toast.LENGTH_LONG)
                        .show()
                }
            }
            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}