package com.ml.chucknorrisapp.di

import com.ml.chucknorrisapp.model.network.JokeApiService
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Module is used by Koin to provide an JokeApiService instance
 */
val apiModule = module {

    fun provideUserApi(retrofit: Retrofit) : JokeApiService {
        return retrofit.create(JokeApiService::class.java)
    }

    single { provideUserApi(get()) }
}