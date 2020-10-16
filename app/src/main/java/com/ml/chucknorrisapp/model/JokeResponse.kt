package com.ml.chucknorrisapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Class is used to retrieve a response from the API
 * @constructor takes three parameters:
 * - A unique ID for the Room database
 * - A List of Joke objects
 */
@Entity
data class JokeResponse (
	var jokeSelectedId: Int = 0,
	@PrimaryKey val jokeResponseId: Int = 1,
	@SerializedName("value") val value : List<Joke>
)
