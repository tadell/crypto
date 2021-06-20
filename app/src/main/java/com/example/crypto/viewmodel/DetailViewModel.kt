package com.example.crypto.viewmodel

import androidx.lifecycle.ViewModel
import com.example.crypto.repository.DetailRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class DetailViewModel() : ViewModel(), CoroutineScope by MainScope() {

    fun getDetails() {
        launch(Dispatchers.Main) {
            try {
                val detailData = DetailRepository().load(id = 1)
            } catch (exception: Exception) {
                exception.message?.let {
                }
            }
        }
    }
}
