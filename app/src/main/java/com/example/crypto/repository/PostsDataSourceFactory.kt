//package com.example.crypto.repository
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.viewModelScope
//import androidx.paging.DataSource
//import androidx.paging.Pager
//import androidx.paging.PagingConfig
//import androidx.paging.cachedIn
//import com.example.crypto.AppController
//import com.example.crypto.model.Data
//
//class PostsDataSourceFactory : DataSource.Factory<String, Data>() {
//
//    val sourceLiveData = MutableLiveData<CryptoRepository>()
//
//    override fun create(): DataSource<String, Data> {
//        val source = CryptoRepository("price")
//        sourceLiveData.postValue(source)
//        return source
//    }
//
//}