package com.ml.chucknorrisapp.di

import com.ml.chucknorrisapp.viewmodel.FavouriteJokeViewModel
import com.ml.chucknorrisapp.viewmodel.JokeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Module is used by Koin to provide a JokeViewModel and
 * FavouriteJokeViewModel instance to the application
 */

val viewModelModule = module {
   viewModel { JokeViewModel(get()) }
   viewModel { FavouriteJokeViewModel(get()) }
}

