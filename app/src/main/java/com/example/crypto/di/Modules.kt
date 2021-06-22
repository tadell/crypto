package com.example.crypto.di

import com.example.crypto.repository.CryptoListRepository
import com.example.crypto.repository.CryptoRepositoryImpl
import com.example.crypto.repository.DetailRepository
import com.example.crypto.view.cryptoList.CryptoListViewModel
import com.example.crypto.view.detail.DetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val myModule = module {
    factory { DetailRepository() }
}

val repoModule = module {
    single<CryptoListRepository> { CryptoRepositoryImpl() }
}


val cryptoListViewModelModule = module {
    viewModel { CryptoListViewModel(get()) }
}


val detailViewModelModule = module {
    viewModel {
        DetailViewModel()
    }
}

