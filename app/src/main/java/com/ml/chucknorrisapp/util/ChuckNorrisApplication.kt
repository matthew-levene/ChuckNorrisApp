package com.ml.chucknorrisapp.util

import android.app.Application
import com.ml.chucknorrisapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class ChuckNorrisApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin(){
        org.koin.core.context.startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@ChuckNorrisApplication)
            modules(networkModule, apiModule, databaseModule, repoModule, viewModelModule)
        }
    }
}