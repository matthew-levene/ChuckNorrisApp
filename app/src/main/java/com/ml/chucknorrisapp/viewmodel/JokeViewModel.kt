package com.ml.chucknorrisapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
import com.ml.chucknorrisapp.model.Joke
import com.ml.chucknorrisapp.model.JokeResponse
import com.ml.chucknorrisapp.repository.JokeRepository
import com.ml.chucknorrisapp.util.LoadingState
import kotlinx.coroutines.*
import retrofit2.HttpException

/**
 * Class is used to service communications between the JokeRepository and the View classes
 */
class JokeViewModel(private val jokeRepository: JokeRepository) : ViewModel(){

    private val job = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Listen for any changes in the database and expose them to the Views
     */
    val jokeLiveData = jokeRepository.jokeLiveData

    /**
     * Listen for explicit joke references retrieved from the repository
     */
    val explicitJokeFound = jokeRepository.explicitJokeFound

    /**
     * Listen for events caused by a joke being clicked on the view
     */
    private var _jokeSelectedProperty = MutableLiveData<JokeResponse>()
    val jokeSelectedProperty: LiveData<JokeResponse>
    get() = _jokeSelectedProperty

    /**
     * Listen for changes in the Loading State and report them to the Views
     */
    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
    get() = _loadingState

    /**
     * Makes a call to check the repository for new jokes from the API on ViewModel initialisation
     */
    init {
        refreshJokes()
    }

    /**
     * Function requests for the repository to check for new jokes
     */
    fun refreshJokes(){
        viewModelScope.launch {
            try {
                _loadingState.value = LoadingState.LOADING
                jokeRepository.getJokes()
                _loadingState.value = LoadingState.LOADED
            }
            catch (exception: HttpException){
                _loadingState.value = LoadingState.logError(exception.message())
            }
        }
    }

    /**
     * Function requeests for the repository to check for a specific joke
     * @param jokeId - ID number to reference the specific joke
     */
    fun searchSpecificJoke(jokeId: Int){
        viewModelScope.launch {
            try {
                _loadingState.value = LoadingState.LOADING
                jokeRepository.getSpecificJoke(jokeId)
                _loadingState.value = LoadingState.LOADED
            }
            catch (exception: HttpException){
                _loadingState.value = LoadingState.logError(exception.message())
            }
        }

    }

    /**
     * Function stores the JokeResponse object for joke click event handling
     * @param jokeResponse
     */
    fun storeJokeResponse(jokeResponse: JokeResponse){
        _jokeSelectedProperty.value = jokeResponse
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}