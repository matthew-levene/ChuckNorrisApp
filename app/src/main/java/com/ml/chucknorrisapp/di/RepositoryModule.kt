package com.ml.chucknorrisapp.di

import com.ml.chucknorrisapp.model.db.JokeDao
import com.ml.chucknorrisapp.model.network.JokeApiService
import com.ml.chucknorrisapp.repository.JokeRepository
import org.koin.dsl.module

/**
 * Module is used by Koin to provide a JokeRepository instance to the application
 */

val repoModule = module {
    fun provideRepository(dao: JokeDao,apiServices: JokeApiService) : JokeRepository {
        return JokeRepository(dao, apiServices)
    }

    single { provideRepository(get(), get()) }
}