package com.ml.chucknorrisapp.model.network

/**
 * Object is used to provide fixed network addresses and endpoints to the application
 */
object NetworkConstants {

    /**
     * Base URL where used to execute all API Endpoint requests
     */
    const val JOKE_BASE_URL = "https://api.icndb.com/"

    /**
     * API Endpoint to get 20 random jokes from the API that are sanitised
     */
    const val GET_RANDOM_JOKES = "jokes/random/20?limitTo=[nerdy]"

    /**
     * URL Address to Chuck Norris Image used by Glide in the Row Joke Item layout
     */
    const val CHUCK_NORRIS_IMAGE = "https://vignette.wikia.nocookie.net/nurdpedia/images/8/87/Chuck-norris.jpg/revision/latest/top-crop/width/360/height/450?cb=20180413072506"

    /**
     * Explicit keyword
     */
    const val EXPLICIT = "explicit"
}