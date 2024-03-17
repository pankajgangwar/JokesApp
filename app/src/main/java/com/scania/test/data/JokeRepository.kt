package com.scania.test.data

import com.google.gson.Gson
import com.scania.test.domain.Joke
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JokeRepository @Inject constructor(private val jokeService: JokeService,
                                         private val gson: Gson,
                                         private val jokesDao: JokesDao) {

    suspend fun searchForJokes(fullUrl : String ): Response<JokeResponseModel?> {
        val response = jokeService.searchJokes(fullUrl)
        if (!response.isSuccess() || response.body()?.error == true) {
            val err = response.errorBody()?.string()
            val errorResponse = gson.fromJson(err, ErrorResponse::class.java)
            Response.Error(Failure(errorResponse.message))
        }
        return Response.Success(response.body())
    }

    suspend fun getAllFavouriteJokes() : Flow<List<Joke>> {
        return jokesDao.getFavouriteJokes()
    }

    suspend fun removeJoke(joke: Joke) {
        return jokesDao.removeJoke(joke)
    }

    suspend fun isFavourite(joke: Joke) : Boolean {
        val res = jokesDao.isFavourite(joke.id)
        return (res.size == 1 && res[0].favourite)
    }

    suspend fun saveJoke(joke : Joke)  {
        return jokesDao.insertJoke(joke)
    }
}
