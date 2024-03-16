package com.scania.test.domain


sealed interface UIState {
    data class Success(val jokes : List<Joke>) : UIState
    data class Error(val message : String) : UIState
    object Loading : UIState
}