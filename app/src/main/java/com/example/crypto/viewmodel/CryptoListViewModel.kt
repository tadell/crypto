package com.example.crypto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.crypto.repository.CryptoRepository

class CryptoListViewModel () : ViewModel() {
    val listData = Pager(PagingConfig(pageSize = 6)) {
        CryptoRepository()
    }.flow.cachedIn(viewModelScope)
}