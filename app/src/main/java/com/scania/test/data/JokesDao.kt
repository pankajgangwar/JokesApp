package com.scania.test.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.scania.test.domain.Joke
import kotlinx.coroutines.flow.Flow

@Dao
interface JokesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveJoke(joke: Joke) : Long

    @Query("SELECT * FROM joke ")
    fun getFavouriteJokes(): Flow<List<Joke>>

    @Delete
    fun removeJoke(joke: Joke)

    @Query("Select * from Joke where id = :id LIMIT 1")
    fun findJoke(id: Int) : Joke?
}