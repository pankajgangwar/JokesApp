package com.scania.test.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.scania.test.domain.Joke
import com.scania.test.domain.JokesRoomUseCase
import com.scania.test.domain.SearchForJokesUseCase
import com.scania.test.domain.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val jokeRoomUseCase : JokesRoomUseCase,
    private val searchForJokesUseCase: SearchForJokesUseCase) : ViewModel() {

    private val TAG = HomeViewModel::class.java.simpleName

    private val _favouriteJokesStateFlow = MutableStateFlow<UIState>(UIState.Loading)
    val favouriteJokesStateFlow = _favouriteJokesStateFlow

    private val _searchJokesStateFlow = MutableStateFlow<UIState>(UIState.Loading)
    val searchJokesStateFlow = _searchJokesStateFlow

    suspend fun getFavouriteJokes(){
        _favouriteJokesStateFlow.emit(UIState.Loading)
        jokeRoomUseCase.getFavouriteJokesUseCase().collectLatest { list ->
            _favouriteJokesStateFlow.emit(UIState.Success(list))
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