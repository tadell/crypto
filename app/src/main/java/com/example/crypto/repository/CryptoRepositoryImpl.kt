package com.example.crypto.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.crypto.model.Data
import androidx.paging.liveData


interface CryptoListRepository {
    suspend fun getCryptoList(context:Context,sortText: String,sortDesc: String,cryptoText: String,tagText: String): LiveData<PagingData<Data>>
}


class CryptoRepositoryImpl() : CryptoListRepository {

    override suspend fun getCryptoList(context:Context,sortText: String,sortDesc: String,cryptoText: String,tagText: String): LiveData<PagingData<Data>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CryptoListPagingSource(context,sortText,sortDesc,cryptoText,tagText)
            }
        ).liveData
    }

}