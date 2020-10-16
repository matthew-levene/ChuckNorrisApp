package com.ml.chucknorrisapp.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ml.chucknorrisapp.model.Joke
import com.ml.chucknorrisapp.model.JokeResponse
import com.ml.chucknorrisapp.model.db.DatabaseConstants.DATABASE_VERSION
import com.ml.chucknorrisapp.model.db.DatabaseConstants.EXPORT_SCHEME_OPTION

/**
 * Class is used to store JokeResponse objects locally
 */
@Database(entities = [JokeResponse::class, Joke::class], version = DATABASE_VERSION, exportSchema = EXPORT_SCHEME_OPTION)
@TypeConverters(Converters::class)
abstract class JokeDatabase : RoomDatabase() {
    abstract val jokeDao: JokeDao
}