package com.ml.chucknorrisapp.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ml.chucknorrisapp.model.JokeResponse
import com.ml.chucknorrisapp.model.db.DatabaseConstants.DATABASE_VERSION
import com.ml.chucknorrisapp.model.db.DatabaseConstants.EXPORT_SCHEME_OPTION

/**
 * Class is used to store JokeResponse objects locally
 */
@Database(entities = [JokeResponse::class], version = DATABASE_VERSION, exportSchema = EXPORT_SCHEME_OPTION)
abstract class JokeDatabase : RoomDatabase() {
    abstract val jokeDao: JokeDao
}