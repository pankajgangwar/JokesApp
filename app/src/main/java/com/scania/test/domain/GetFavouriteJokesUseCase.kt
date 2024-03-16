package com.scania.test.domain

import com.scania.test.data.JokeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavouriteJokesUseCase @Inject constructor(private val jokeRepository: JokeRepository) {

    suspend operator fun invoke() : Flow<List<Joke>>  {
        return jokeRepository.getAllFavouriteJokes()
    }
}