//package com.example.crypto.repository
//
//import androidx.lifecycle.MutableLiveData
//import androidx.paging.PageKeyedDataSource
//import androidx.paging.PagingSource
//import com.example.crypto.model.Data
//import com.example.crypto.network.ApiClient
//import com.example.crypto.network.ApiService
//import kotlinx.coroutines.Job
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class PostsDataSource: PageKeyedDataSource<String, Data>() {
//    private val apiService = ApiClient.getClient().create(ApiService::class.java)
//
//
//
//    override fun loadInitial(
//        params: LoadInitialParams<String>,
//        callback: LoadInitialCallback<String, Data>
//    ) {
//        val response = apiService.getCryptoList("",
//            "20",
//            "price")
//        val data = response.body()?.data
//        val items = data?.children?.map { it.data } ?: emptyList()
//        callback.onResult(items, data?.before, data?.after)
//    }
//
//
//    override fun loadBefore(
//        params: LoadParams<String>,
//        callback: LoadCallback<String, Data>
//    ) {
//
//    }
//
//    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Data>) {
//        TODO("Not yet implemented")
//    }
//
//}