package com.ml.chucknorrisapp.viewmodel

import androidx.lifecycle.ViewModel
import com.ml.chucknorrisapp.model.Joke
import com.ml.chucknorrisapp.repository.JokeRepository

/**
 * Class is used to service favourite joke requests within the application
 */
class FavouriteJokeViewModel(
    private val jokeRepository: JokeRepository
) : ViewModel(){

    /**
     * Listens out for changes within the favourite table in the JokeRepository class
     */
    val favouriteJokeLiveData = jokeRepository.favouriteJokesLiveData

    /**
     * Function saves a favourite joke to the database
     */
    fun addFavouriteJoke(joke: Joke){
        jokeRepository.insertFavouriteJoke(joke)
    }

    /**
     * Function deletes a favourite joke from the database
     */
    fun deleteFavouriteJoke(joke: Joke){
        jokeRepository.deleteFavouriteJoke(joke)
    }

}