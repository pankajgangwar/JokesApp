package com.scania.test.domain

import com.scania.test.data.JokeRepository
import javax.inject.Inject

class SaveJokeUseCase @Inject constructor(private val jokeRepository: JokeRepository) {

    suspend operator fun invoke(joke: Joke) : Long {
        return jokeRepository.saveJoke(joke)
    }

}