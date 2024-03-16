package com.scania.test.presentation

import androidx.lifecycle.ViewModel
import com.scania.test.domain.Joke
import com.scania.test.domain.JokesRoomUseCase
import com.scania.test.domain.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val jokeRoomUseCase : JokesRoomUseCase) : ViewModel() {

    private val _favouriteJokesStateFlow =
        MutableStateFlow<UIState>(UIState.Loading)
    val favouriteJokesStateFlow = _favouriteJokesStateFlow

    suspend fun getFavouriteJokes(){
        _favouriteJokesStateFlow.emit(UIState.Loading)
        jokeRoomUseCase.getFavouriteJokesUseCase.invoke().collectLatest { list ->
            _favouriteJokesStateFlow.emit(UIState.Success(list))
        }
    }

    suspend fun removeJoke(joke: Joke){
        jokeRoomUseCase.removeJokeUseCase.invoke(joke)
    }

    suspend fun saveJoke(joke: Joke){
        jokeRoomUseCase.saveJokeUseCase.invoke(joke)
    }
}