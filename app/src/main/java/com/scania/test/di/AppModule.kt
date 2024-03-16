package com.scania.test.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.scania.test.data.AppDatabase
import com.scania.test.data.JokeRepository
import com.scania.test.data.JokeService
import com.scania.test.data.JokesDao
import com.scania.test.domain.GetFavouriteJokesUseCase
import com.scania.test.domain.JokesRoomUseCase
import com.scania.test.domain.RemoveJokeUseCase
import com.scania.test.domain.SaveJokeUseCase
import com.scania.test.domain.SearchForJokesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)

        return clientBuilder.addInterceptor(logger).build()
    }

    @Provides
    fun provideGson() : Gson {
        return GsonBuilder().create()
    }

    /*Services*/
    @Provides
    fun provideJokeApiService(okHttpClient: OkHttpClient, gson: Gson): JokeService {
        return Retrofit.Builder()
            .baseUrl("https://v2.jokeapi.dev/joke/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(JokeService::class.java)
    }

    /*Repositories*/
    @Provides
    @Singleton
    fun provideJokeRepository(jokeService: JokeService, gson: Gson,
                              jokesDao: JokesDao): JokeRepository {
        return JokeRepository(jokeService, gson, jokesDao)
    }

    /*UseCases*/
    @Provides
    @Singleton
    fun provideSearchJokesUseCase(repository: JokeRepository) : SearchForJokesUseCase {
        return SearchForJokesUseCase(repository)
    }

    /*UseCases*/
    @Provides
    @Singleton
    fun provideRoomJokesUseCase(repository: JokeRepository) : JokesRoomUseCase {
        return JokesRoomUseCase(GetFavouriteJokesUseCase(repository),
            RemoveJokeUseCase(repository), SaveJokeUseCase(repository))
    }

    /*Room Dao*/
    @Provides
    @Singleton
    fun provideJokeDao(@ApplicationContext context: Context) : JokesDao {
        return AppDatabase.getInstance(context).jokesDao()
    }

}