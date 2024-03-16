package com.scania.test.domain

data class JokesRoomUseCase(
    val getFavouriteJokesUseCase: GetFavouriteJokesUseCase,
    val removeJokeUseCase: RemoveJokeUseCase,
    val saveJokeUseCase: SaveJokeUseCase
)