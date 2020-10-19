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
     * Explicit keyword
     */
    const val EXPLICIT = "explicit"
}