package com.scania.test.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scania.test.domain.Joke
import com.scania.test.domain.JokesRoomUseCase
import com.scania.test.domain.SearchForJokesUseCase
import com.scania.test.domain.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import kotlin.concurrent.Volatile

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val jokeRoomUseCase : JokesRoomUseCase,
    private val searchForJokesUseCase: SearchForJokesUseCase) : ViewModel() {

    private val TAG = HomeViewModel::class.java.simpleName

    private val _favouriteJokesStateFlow = MutableStateFlow<UIState>(UIState.Loading)
    val favouriteJokesStateFlow = _favouriteJokesStateFlow

    private val _searchJokesStateFlow = MutableStateFlow<UIState>(UIState.Loading)
    val searchJokesStateFlow = _searchJokesStateFlow

    @Volatile
    var hasFavouriteJokes = false
    fun hasFavouriteJokesSaved() : Boolean {
        return hasFavouriteJokes
    }

    fun getFavouriteJokes(){
        viewModelScope.launch(Dispatchers.IO) {
            _favouriteJokesStateFlow.emit(UIState.Loading)
            jokeRoomUseCase.getFavouriteJokesUseCase().collectLatest { list ->
                hasFavouriteJokes = list.isNotEmpty()
                _favouriteJokesStateFlow.emit(UIState.Success(list))
            }
        }
    }

    suspend fun searchForJokes(url : String){
        _searchJokesStateFlow.emit(UIState.Loading)
        searchForJokesUseCase(url).collectLatest { uiState ->
            when(uiState){
                is UIState.Success -> {
                    val jokeList = uiState.jokes
                    for(joke in jokeList ){
                        joke.favourite = jokeRoomUseCase.favouriteUseCase(joke)
                        //Log.d(TAG, "isFavourite ${joke.id} -> ${joke.favourite}")
                    }
                }
                else -> {}
            }
            _searchJokesStateFlow.emit(uiState)
        }
    }

    suspend fun removeJoke(joke: Joke){
        jokeRoomUseCase.removeJokeUseCase(joke)
    }

    suspend fun saveJoke(joke: Joke){
        jokeRoomUseCase.saveJokeUseCase(joke)
    }
}