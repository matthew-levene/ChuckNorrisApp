package com.ml.chucknorrisapp.model

import com.google.gson.annotations.SerializedName

data class SpecificJoke(
    @SerializedName("value") val joke: Joke
)