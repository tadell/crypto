package com.example.crypto

import android.app.Application
import com.example.crypto.di.*
import com.example.crypto.model.enums.CryptoType
import com.example.crypto.model.enums.SortType
import com.example.crypto.model.enums.TagType
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.*

open class AppController : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            // declare used Android context
            androidContext(this@AppController)
            // declare modules
            modules(
                detailRepoModule,
                cryptoRepoModule,
                detailViewModelModule,
                cryptoListViewModelModule
            )
        }
    }

}