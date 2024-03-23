package com.scania.test.data

import android.util.Log
import com.google.gson.Gson
import com.scania.test.domain.Joke
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JokeRepository @Inject constructor(private val jokeService: JokeService,
                                         private val gson: Gson,
                                         private val jokesDao: JokesDao) {

    private val TAG = JokeRepository::class.java.simpleName
    suspend fun searchForJokes(fullUrl : String ): Response<JokeResponseModel?> {
        val response = jokeService.searchJokes(fullUrl)
        //Log.e(TAG, "Error isSuccess ${!response.isSuccess()} error : ${response.body()?.error}")
        if (!response.isSuccess() || response.body()?.error == true) {
            Response.Error(Failure(response.body()?.message ?: "No jokes found"))
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
        val res = jokesDao.findJoke(joke.id)
        return (res != null)
    }

    suspend fun saveJoke(joke : Joke) : Long  {
        return jokesDao.saveJoke(joke)
    }
}
