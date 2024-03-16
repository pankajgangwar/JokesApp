package com.scania.test.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.scania.test.domain.Joke

@Database(entities = [Joke::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun jokesDao(): JokesDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "jokes_db")
                .addCallback(object : Callback() {})
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}