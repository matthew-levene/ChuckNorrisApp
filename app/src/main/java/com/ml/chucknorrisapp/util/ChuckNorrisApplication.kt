package com.ml.chucknorrisapp.util

import android.app.Application
import androidx.work.*
import com.ml.chucknorrisapp.di.*
import com.ml.chucknorrisapp.model.Joke
import com.ml.chucknorrisapp.util.background.JokeWorker
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import java.util.concurrent.TimeUnit

class ChuckNorrisApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
        startWorkManager()
    }

    private fun startKoin(){
        org.koin.core.context.startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@ChuckNorrisApplication)
            modules(networkModule, apiModule, databaseModule, repoModule, viewModelModule)
        }
    }

    private fun startWorkManager(){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(false)
            .build()

        //Set the frequency at which the job should be executed
        val repeatingRequest = PeriodicWorkRequestBuilder<JokeWorker>(1, TimeUnit.HOURS)
            //Add the device constraints
            .setConstraints(constraints)
            .build()

        //Schedule the work to be completed
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            JokeWorker.WORK_LOCATION,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}