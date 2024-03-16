package com.scania.test.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JokeService {

    @GET("{fullUrl}")
    suspend fun searchJokes(
        @Path(value = "fullUrl") fullUrl : String,
        @Query("blacklistFlags") blacklistFlags : String)
    : Response<JokeResponseModel>

}