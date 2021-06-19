package com.example.crypto

import android.app.Application
import com.example.crypto.di.myModule
import com.example.crypto.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class AppController :Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // declare used Android context
            androidContext(this@AppController)
            // declare modules
            modules(myModule, viewModelModule)
        }
    }
}