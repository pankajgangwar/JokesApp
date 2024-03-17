package com.scania.test.domain

import com.scania.test.data.JokeRepository
import javax.inject.Inject

class IsJokeFavouriteUseCase @Inject constructor(private val jokeRepository: JokeRepository) {

    suspend operator fun invoke(joke : Joke) : Boolean {
        return jokeRepository.isFavourite(joke)
    }
}