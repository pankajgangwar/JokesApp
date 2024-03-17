package com.scania.test.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface JokeService {

    @GET
    suspend fun searchJokes(@Url fullUrl : String): Response<JokeResponseModel>

}