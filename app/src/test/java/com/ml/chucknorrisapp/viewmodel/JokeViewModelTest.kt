package com.ml.chucknorrisapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ml.chucknorrisapp.repository.JokeRepository
import com.ml.chucknorrisapp.util.CoroutineTestRule
import com.ml.chucknorrisapp.util.LifeCycleTestOwner
import com.ml.chucknorrisapp.util.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mockito.*
import java.lang.NullPointerException

@ExperimentalCoroutinesApi
class JokeViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var loadingStateObserver: Observer<LoadingState>

    private lateinit var jokeRepository: JokeRepository

    private lateinit var jokeViewModel: JokeViewModel

    private lateinit var lifeCycleTestOwner: LifeCycleTestOwner

    @Before
    fun setUp() {
        lifeCycleTestOwner = LifeCycleTestOwner()
        lifeCycleTestOwner.onCreate()

        jokeRepository =  mock(JokeRepository::class.java)

        jokeViewModel = JokeViewModel(jokeRepository)

        loadingStateObserver = mock(Observer::class.java) as Observer<LoadingState>
        jokeViewModel.loadingState.observe(lifeCycleTestOwner, loadingStateObserver)
    }

    @Test
    fun test_refreshJokes() {
        coroutineTestRule.testDispatcher.runBlockingTest {

            // Given
            lifeCycleTestOwner.onResume()

            `when`(jokeRepository.getJokes()).thenReturn(null)

            // When
            jokeViewModel.refreshJokes()

            // Then
            verify(jokeRepository, times(2)).getJokes()
            verify(loadingStateObserver).onChanged(LoadingState.LOADING)
        }
    }

    @Test
    fun test_searchSpecificJoke() {
        coroutineTestRule.testDispatcher.runBlockingTest {

            // Given
            lifeCycleTestOwner.onResume()

            `when`(jokeRepository.getSpecificJoke(1)).thenReturn(null)

            // When
            jokeViewModel.searchSpecificJoke(1)

            // Then
            verify(jokeRepository, times(1)).getSpecificJoke(1)
            verify(loadingStateObserver).onChanged(LoadingState.LOADING)
        }
    }

    @After
    fun tearDown(){
        lifeCycleTestOwner.onDestroy()
    }

}