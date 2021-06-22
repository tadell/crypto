package com.example.crypto.view.cryptoList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.crypto.model.Data
import com.example.crypto.repository.CryptoListRepository
import com.example.crypto.view.base.BaseViewModel


open class CryptoListViewModel(private val repository: CryptoListRepository) : BaseViewModel() {

    private val _cryptoList = MutableLiveData<PagingData<Data>?>()

    suspend fun getCryptoList(sortText: String,sortDesc: String,cryptoText: String,tagText: String): LiveData<PagingData<Data>> {
        val response = repository.getCryptoList(sortText,sortDesc,cryptoText,tagText).cachedIn(viewModelScope)
        _cryptoList.value = null
        _cryptoList.value = response.value
        return response
    }
}