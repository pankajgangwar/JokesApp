package com.scania.test.domain

import com.scania.test.data.JokeRepository
import com.scania.test.data.JokeResponseModel
import com.scania.test.data.Response
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchForJokesUseCase @Inject constructor(private val jokeRepository: JokeRepository) {

    suspend operator fun invoke(fullUrl : String, blacklistFlags : String) = flow {
        try {
            when (val response = jokeRepository.searchForJokes(fullUrl, blacklistFlags)) {
                is Response.Success -> {
                    if(response.data?.error == true){
                        emit(UIState.Error("No jokes available"))
                    } else {
                        emit(
                            UIState.Success(jokesResponseMapper(response.data))
                        )
                    }
                }
                is Response.Error -> {
                    emit(UIState.Error(response.error.message))
                }
            }
        }catch (e : Exception){
            val message = "Failed to find jokes"
            emit(UIState.Error(message))
        }
    }

    private fun jokesResponseMapper(response : JokeResponseModel? ): List<Joke> {
        val jokeArrayList = arrayListOf<Joke>()
        if(response?.jokes.isNullOrEmpty()) {
            return jokeArrayList
        }
        for(joke in response?.jokes!!){
            val item = Joke( delivery = joke.delivery,
                flags = Joke.Flags(sexist = joke.flags.sexist,
                    nsfw = joke.flags.nsfw, explicit = joke.flags.explicit,
                    political = joke.flags.political, racist = joke.flags.racist,
                    religious = joke.flags.religious))
            jokeArrayList.add(item)
        }
        return jokeArrayList
    }
}