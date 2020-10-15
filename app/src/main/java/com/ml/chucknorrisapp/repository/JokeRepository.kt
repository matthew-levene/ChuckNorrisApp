package com.ml.chucknorrisapp.repository

import com.ml.chucknorrisapp.model.db.JokeDao
import com.ml.chucknorrisapp.model.network.JokeApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 * Class is used to provide communications between the remote and local data sources and
 * ViewModel classes in the application
 *
 * @constructor takes two parameters
 * - An instance of type JokeDao
 * - An instance of type JokeApiService
 */
class JokeRepository(
    private val jokeDao: JokeDao,
    private val jokeApiService: JokeApiService
){
    /**
     * Function calls the remote API source and retrieves a JokeResponse object
     * to be stored within the local database
     */
    suspend fun getJokes(){
        withContext(Dispatchers.IO){
            try {
                val jokeResponse = jokeApiService.getRandomJokes().await()
                jokeDao.deleteAllJokes()
                jokeDao.insertJokes(jokeResponse)
            }catch (exception: HttpException){
                throw HttpException(exception.response())
            }
        }
    }

    /**
     * Listen for any changes within the JokeResponse table and reports them back to any
     * interested ViewModel classes.
     */
    val jokeLiveData = jokeDao.getJokes()
}