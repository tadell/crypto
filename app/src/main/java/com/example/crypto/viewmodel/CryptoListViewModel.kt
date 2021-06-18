package com.example.crypto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.crypto.network.ApiService
import com.example.crypto.repository.CryptoRepository

class CryptoListViewModel (private val apiService: ApiService) : ViewModel() {

    val listData = Pager(PagingConfig(pageSize = 20)) {
        CryptoRepository(apiService)
    }.flow.cachedIn(viewModelScope)
}