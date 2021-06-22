package com.example.crypto

import android.app.Application
import com.example.crypto.di.*
import com.example.crypto.model.enums.SortType
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.*

open class AppController : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // declare used Android context
            androidContext(this@AppController)
            // declare modules
            modules(
                myModule,
                repoModule,
                detailViewModelModule,
                cryptoListViewModelModule
            )
        }
    }

    companion object {
        @JvmField
        var public_sort_text: String = SortType.MARKET_CAP.toString().toLowerCase(Locale.ROOT)
    }
}