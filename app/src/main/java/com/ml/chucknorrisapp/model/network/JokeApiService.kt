package com.ml.chucknorrisapp.model.network

import com.ml.chucknorrisapp.model.Joke
import com.ml.chucknorrisapp.model.JokeResponse
import com.ml.chucknorrisapp.model.SpecificJoke
import com.ml.chucknorrisapp.model.network.NetworkConstants.GET_RANDOM_JOKES
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interface is used by Retrofit to execute API requests
 */
interface JokeApiService {
    /**
     * Function will retrieve a JokeResponse object containing
     * a list of 20 sanitised jokes from the API
     */
    @GET(GET_RANDOM_JOKES)
    fun getRandomJokes() : Deferred<JokeResponse>

    /**
     * Function will retrieve a JokeResponse object containing
     * a joke that matches the search ID.
     */
    @GET("https://api.icndb.com/jokes/{id}?limitTo=[nerdy]")
    fun getSpecificJoke(@Path("id") jokeId: Int) : Deferred<SpecificJoke>

}