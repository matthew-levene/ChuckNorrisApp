package com.ml.chucknorrisapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ml.chucknorrisapp.model.Joke
import com.ml.chucknorrisapp.repository.JokeRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class FavouriteJokeViewModelTest {

    private lateinit var jokeRepository: JokeRepository

    private lateinit var favouriteJokeViewModel: FavouriteJokeViewModel


    private lateinit var joke:Joke

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {

        jokeRepository = mock(JokeRepository::class.java)

        favouriteJokeViewModel = FavouriteJokeViewModel(jokeRepository)

        joke = Joke(0, "Test", emptyList())
    }

    @Test
    fun addFavouriteJoke() {
        doNothing().`when`(jokeRepository).insertFavouriteJoke(joke)

        favouriteJokeViewModel.addFavouriteJoke(joke)

        verify(jokeRepository).insertFavouriteJoke(joke)
    }

    @Test
    fun deleteFavouriteJoke() {
        doNothing().`when`(jokeRepository).deleteFavouriteJoke(joke)

        favouriteJokeViewModel.deleteFavouriteJoke(joke)

        verify(jokeRepository).deleteFavouriteJoke(joke)
    }
}