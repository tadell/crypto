package com.example.crypto.view.detail

import androidx.lifecycle.MutableLiveData
import com.example.crypto.model.InfoData
import com.example.crypto.repository.DetailRepository
import com.example.crypto.view.base.BaseViewModel
import kotlinx.coroutines.*

class DetailViewModel() : BaseViewModel(), CoroutineScope by MainScope() {
    val detailData = MutableLiveData<InfoData>()
    var job: Job? = null
    val loading = MutableLiveData<Boolean>()

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getDetails(id: Int) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = DetailRepository().load(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    detailData.postValue(response.body()?.data?.get(id.toString()))
                    loading.value = false

                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
