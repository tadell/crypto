package com.example.crypto.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.crypto.model.Data
import com.example.crypto.network.ApiService
import androidx.paging.liveData

interface CryptoListRepository {
    suspend fun getCryptoList(sortText:String): LiveData<PagingData<Data>> }

class CryptoRepositoryImpl() : CryptoListRepository {


    override suspend fun getCryptoList(sortText:String): LiveData<PagingData<Data>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CryptoListPagingSource(sortText)
            }
        ).liveData
    }
}