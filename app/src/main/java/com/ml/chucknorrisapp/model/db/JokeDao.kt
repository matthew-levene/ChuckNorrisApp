package com.ml.chucknorrisapp.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ml.chucknorrisapp.model.Joke
import com.ml.chucknorrisapp.model.JokeResponse
import com.ml.chucknorrisapp.model.db.DatabaseConstants.DELETE_ALL_JOKES
import com.ml.chucknorrisapp.model.db.DatabaseConstants.SELECT_ALL_FAVOURITE_JOKES
import com.ml.chucknorrisapp.model.db.DatabaseConstants.SELECT_ALL_JOKES
import retrofit2.http.DELETE

/**
 * Interface is used by the Room database to execute data access requests
 */
@Dao
interface JokeDao {

    /**
     * Function inserts a JokeResponse object into the JokeResponse table
     * If a similar object already exists, the database will replace it
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertJokes(jokeResponse: JokeResponse)

    /**
     * Function retrieves all Jokes stored within the JokeResponse table
     */
    @Query(SELECT_ALL_JOKES)
    fun getJokes() : LiveData<JokeResponse>

    /**
     * Function deletes all entries within the JokeResponse table
     */
    @Query(DELETE_ALL_JOKES)
    fun  deleteAllJokes()

    /**
     * Function inserts a favourite joke into the Joke table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavouriteJoke(joke: Joke)

    /**
     * Function retrieves all favourite jokes from the Joke table
     */
    @Query(SELECT_ALL_FAVOURITE_JOKES)
    fun getFavouriteJokes() : LiveData<List<Joke>>


    /**
     * Function deletes a favourite joke from the Joke table
     */
    @Delete
    fun deleteFavouriteJoke(joke: Joke)



}