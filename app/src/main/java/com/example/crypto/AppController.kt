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

    companion object {
        @JvmField
        var public_sort_text: String = SortType.MARKET_CAP.toString().toLowerCase(Locale.ROOT)
        var public_tag_text: String = TagType.ALL.toString().toLowerCase(Locale.ROOT)
        var public_crypto_text: String = CryptoType.ALL.toString().toLowerCase(Locale.ROOT)
    }
}