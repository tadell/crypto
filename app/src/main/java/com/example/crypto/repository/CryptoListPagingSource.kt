package com.example.crypto.repository

import android.content.Context
import android.widget.Toast
import androidx.paging.PagingSource
import com.example.crypto.R
import com.example.crypto.helper.Constants.Companion.PAGE_SIZE
import com.example.crypto.model.Data
import com.example.crypto.network.ApiClient
import com.example.crypto.network.ApiService

private const val  ERROR_CODE_400: Int = 400
private const val  ERROR_CODE_401: Int = 401
private const val  ERROR_CODE_402: Int = 402
private const val  ERROR_CODE_403: Int = 403
private const val  ERROR_CODE_429: Int = 429
private const val  ERROR_CODE_500: Int = 500

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
                PAGE_SIZE,
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

                // handling server error by their code documented in coinmarket site
                response.code() == ERROR_CODE_400 -> {
                    Toast.makeText(context, R.string.Bad_Request_string, Toast.LENGTH_LONG)
                        .show()
                }
                response.code() == ERROR_CODE_401 -> {
                    Toast.makeText(context, R.string.Unauthorized_string, Toast.LENGTH_LONG)
                        .show()
                }
                response.code() == ERROR_CODE_402  -> {
                    Toast.makeText(context, R.string.Payment_Required_string, Toast.LENGTH_LONG)
                        .show()
                }
                response.code() == ERROR_CODE_403  -> {
                    Toast.makeText(context, R.string.Forbidden_string, Toast.LENGTH_LONG)
                        .show()
                }
                response.code() == ERROR_CODE_429  -> {
                    Toast.makeText(context, R.string.Too_Many_Requests_string, Toast.LENGTH_LONG)
                        .show()
                }
                response.code() == ERROR_CODE_500  -> {
                    Toast.makeText(context, R.string.Internal_Server_Error_string, Toast.LENGTH_LONG)
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