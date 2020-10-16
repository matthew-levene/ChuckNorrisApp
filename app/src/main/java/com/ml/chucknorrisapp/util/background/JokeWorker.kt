package com.ml.chucknorrisapp.util.background

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ml.chucknorrisapp.repository.JokeRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Class is used by the WorkManager to schedule work to be completed in the background
 * even when the app is not running or has gone into the background
 */
class JokeWorker (appContext:Context, parameters: WorkerParameters) :
    CoroutineWorker(appContext, parameters), KoinComponent {

    private val jokeRepository: JokeRepository by inject()

    companion object {
        const val WORK_LOCATION = "com.ml.chucknorrisapp.util.background.PetsWorker"
    }

    /**
     * Sync the backend API data with local database even if user is not using the app or device restarts
     */
    override suspend fun doWork(): Result {
        try {
            jokeRepository.getJokes()
        } catch (e: Exception) {
            return Result.retry()
        }
        return Result.success()
    }
}