package com.ml.chucknorrisapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Class is used to represent each joke retrieved from the API
 * @constructor takes three parameters:
 * - A unique ID for the Joke
 * - A String literal containing the joke
 * - A List of categories the joke falls into to
 */
@Entity
data class Joke (

	@PrimaryKey @SerializedName("id") val id : Int,
	@SerializedName("joke") val joke : String,
	@SerializedName("categories") val categories : List<String>
)