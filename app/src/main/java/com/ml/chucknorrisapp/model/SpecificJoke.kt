package com.ml.chucknorrisapp.model

import com.google.gson.annotations.SerializedName

/**
 * Class is used as a placeholder when retrieve specific jokes from the API
 * @constructor takes three parameters:
 * - A Joke instance
 */
data class SpecificJoke(
    @SerializedName("value") val joke: Joke
)