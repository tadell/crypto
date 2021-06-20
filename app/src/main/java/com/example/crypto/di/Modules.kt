package com.example.crypto.di

import com.example.crypto.repository.CryptoRepository
import com.example.crypto.repository.DetailRepository
import com.example.crypto.viewmodel.CryptoListViewModel
import com.example.crypto.viewmodel.DetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {
    factory { CryptoRepository() }
    factory { DetailRepository() }

}
val viewModelModule = module {

    viewModel {
        CryptoListViewModel()

    }
    viewModel {
        DetailViewModel()
    }

}