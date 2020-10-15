package com.ml.chucknorrisapp.model.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.ml.chucknorrisapp.model.Joke

/**
 * Class is used by Room database to convert unknown data types
 * to JSON (String format) and then back to their original data types
 * when retrieved from the database.
 */
class Converters {

    /**
     * Function converts list of Joke objects to String
     * @param jokes
     * @return String representation of the list
     */
    @TypeConverter
    fun jokeListToJson(jokes: List<Joke>) : String{
        return Gson().toJson(jokes)
    }

    /**
     * Function takes a parameter of string and retrieves a list
     * @param value
     * @return List of Joke objects
     */
    @TypeConverter
    fun jokeListFromJson(value: String): List<Joke> {
        return Gson().fromJson(value, Array<Joke>::class.java).toList()
    }
}