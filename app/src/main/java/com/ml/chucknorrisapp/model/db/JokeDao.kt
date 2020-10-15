package com.ml.chucknorrisapp.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ml.chucknorrisapp.model.JokeResponse
import com.ml.chucknorrisapp.model.db.DatabaseConstants.DELETE_ALL_JOKES
import com.ml.chucknorrisapp.model.db.DatabaseConstants.SELECT_ALL_JOKES

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


}