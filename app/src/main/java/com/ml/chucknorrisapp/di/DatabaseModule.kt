package com.ml.chucknorrisapp.di

import android.app.Application
import androidx.room.Room
import com.ml.chucknorrisapp.model.db.DatabaseConstants.DATABASE_NAME
import com.ml.chucknorrisapp.model.db.JokeDao
import com.ml.chucknorrisapp.model.db.JokeDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Module is used by Koin to provide the Database and Dao instances to the application
 */
val databaseModule = module {

    fun provideDatabase(application: Application) : JokeDatabase {
        return Room.databaseBuilder(application, JokeDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    fun provideDAO(database: JokeDatabase) : JokeDao {
        return database.jokeDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideDAO(get()) }
}