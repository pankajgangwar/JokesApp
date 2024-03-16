package com.scania.test.domain

import com.scania.test.data.JokeRepository
import javax.inject.Inject

class RemoveJokeUseCase @Inject constructor(private val jokeRepository: JokeRepository) {

    suspend operator fun invoke(joke : Joke) {
        return jokeRepository.removeJoke(joke)
    }
}