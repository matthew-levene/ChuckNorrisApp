package com.ml.chucknorrisapp.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ml.chucknorrisapp.model.JokeResponse
import com.ml.chucknorrisapp.model.db.JokeDao
import com.ml.chucknorrisapp.model.network.JokeApiService
import com.ml.chucknorrisapp.model.network.NetworkConstants.EXPLICIT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
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
     * Listen out for any explicit jokes
     */
    private var _explicitJokeFound = MutableLiveData<String>()
    val explicitJokeFound: LiveData<String>
    get() = _explicitJokeFound

    /**
     * Function calls the remote API source and retrieves a JokeResponse object
     * to be stored within the local database
     */
    suspend fun getJokes(){
        withContext(Dispatchers.IO){
            try {
                val jokeResponse = jokeApiService.getRandomJokes().await()
                    //jokeDao.deleteAllJokes()
                jokeDao.insertJokes(jokeResponse)
            }catch (exception: HttpException){
                throw HttpException(exception.response())
            }
        }
    }

    /**
     * Function retrieves a specific joke using the given parameter
     * If the joke is categorised as explicit, then the user
     * Joke ViewModel and Views and notified.
     * @param jokeId - ID number that references the joke
     */
    suspend fun getSpecificJoke(jokeId:Int){
            try {
                var isExplicit = false
                withContext(Dispatchers.IO) {
                    val joke = jokeApiService.getSpecificJoke(jokeId).await().joke

                    if (joke.categories.contains(EXPLICIT)) {
                        isExplicit = true
                        return@withContext
                    }

                    jokeDao.deleteAllJokes()
                    jokeDao.insertJokes(JokeResponse(1, listOf(joke)))
                }

                if (isExplicit){
                    _explicitJokeFound.value = EXPLICIT
                }
            }catch (exception: HttpException){
                throw HttpException(exception.response())
        }
    }

    /**
     * Listen for any changes within the JokeResponse table and reports them back to any
     * interested ViewModel classes.
     */
    val jokeLiveData = jokeDao.getJokes()
}