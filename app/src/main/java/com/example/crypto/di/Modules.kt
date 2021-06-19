package com.example.crypto.di

import com.example.crypto.repository.CryptoRepository
import com.example.crypto.viewmodel.CryptoListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {
    factory { CryptoRepository() }

}
val viewModelModule = module {

    viewModel {
        CryptoListViewModel()
    }

}