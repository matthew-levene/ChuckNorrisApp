package com.ml.chucknorrisapp.di

import com.ml.chucknorrisapp.viewmodel.FavouriteJokeViewModel
import com.ml.chucknorrisapp.viewmodel.JokeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
   viewModel { JokeViewModel(get()) }
   viewModel { FavouriteJokeViewModel(get()) }
}

