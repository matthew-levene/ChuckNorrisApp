package com.ml.chucknorrisapp.repository

import com.ml.chucknorrisapp.model.Joke
import com.ml.chucknorrisapp.model.JokeResponse
import com.ml.chucknorrisapp.model.SpecificJoke
import com.ml.chucknorrisapp.model.db.JokeDao
import com.ml.chucknorrisapp.model.network.JokeApiService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.koin.java.KoinJavaComponent.inject
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class JokeRepositoryTest  {

    private lateinit var jokeDao:JokeDao
    private lateinit var jokeApiService: JokeApiService

    private lateinit var jokeRepository:JokeRepository

    private lateinit var jokeResponse:JokeResponse

    private lateinit var argCaptor: ArgumentCaptor<JokeResponse>

    @Before
    fun setUp() {
        jokeDao = mock(JokeDao::class.java)
        jokeApiService = mock(JokeApiService::class.java)

        jokeRepository = JokeRepository(jokeDao, jokeApiService)

        argCaptor = ArgumentCaptor.forClass(JokeResponse::class.java)

        val jokesList:List<Joke> = listOf(
            Joke(1, "Hello World", emptyList()),
            Joke(20, "Testing", listOf("Nerdy", "Explicit"))
        )

        jokeResponse = JokeResponse(0,1, jokesList)
    }

    /**
     * Test relies on real JokeResponse instance from Rest API Service
     * to save to database and as such will fail if saveJokesToDatabase() call
     * inside the function is not commented out.
     *
     * saveJokesToDatabase() has been tested in isolation and worked as expected.
     */
    @Test
    fun test_getJokes(): Unit = runBlocking {
        val deferredResponse = mock(Deferred::class.java)
        `when`(jokeApiService.getRandomJokes()).thenReturn(deferredResponse as Deferred<JokeResponse>?)

        jokeRepository.getJokes()

        verify(jokeApiService, times(1)).getRandomJokes()
    }


    /**
     * Test relies on real SpecificJoke instance from Rest API Service
     * to evaluate and save to database and as such will fail if lines
     * 64 to 72 are not commented out.
     *
     * saveJokesToDatabase() has been tested in isolation and worked as expected.
     */
    @Test
    fun test_getSpecificJoke(): Unit = runBlocking {
        val deferredResponse = mock(Deferred::class.java)
        `when`(jokeApiService.getSpecificJoke(5)).thenReturn(deferredResponse as Deferred<SpecificJoke>?)

        jokeRepository.getSpecificJoke(5)

        verify(jokeApiService, times(1)).getSpecificJoke(5)
    }

    @Test
    fun test_saveJokesToDatabase(){
        doNothing().`when`(jokeDao).insertJokes(jokeResponse)

        jokeRepository.saveJokesToDatabase(jokeResponse)

        verify(jokeDao, times(1)).insertJokes(jokeResponse)
    }


    @Test
    fun test_insertFavouriteJoke() {
        doNothing().`when`(jokeDao).insertFavouriteJoke(Joke(1,"Testing", emptyList()))

        jokeRepository.insertFavouriteJoke(Joke(1,"Testing", emptyList()))

        verify(jokeDao, times(1)).insertFavouriteJoke(Joke(1,"Testing", emptyList()))
    }

    @Test
    fun test_deleteFavouriteJoke() {
        doNothing().`when`(jokeDao).deleteFavouriteJoke(Joke(1,"Testing", emptyList()))

        jokeRepository.deleteFavouriteJoke(Joke(1,"Testing", emptyList()))

        verify(jokeDao, times(1)).deleteFavouriteJoke(Joke(1,"Testing", emptyList()))
    }
}