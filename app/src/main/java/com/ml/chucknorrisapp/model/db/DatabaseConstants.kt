package com.ml.chucknorrisapp.model.db

/**
 * Object is used to provide fixed database information to the application
 */
object DatabaseConstants {
    /**
     * Name of the database
     */
    const val DATABASE_NAME = "jokes.db"

    /**
     * Version number of the database
     */
    const val DATABASE_VERSION = 1

    /**
     * Specifies whether it has the ability to export the schema
     */
    const val EXPORT_SCHEME_OPTION = false

    /**
     * Query to delete everything from the JokeResponse table
     */
    const val DELETE_ALL_JOKES = "DELETE FROM JokeResponse"

    /**
     * Query to retrieve everything from the JokeResponse table
     */
    const val SELECT_ALL_JOKES = "SELECT * FROM JokeResponse"

    /**
     * Query to retrieve everything from the Joke table
     */
    const val SELECT_ALL_FAVOURITE_JOKES = "SELECT * FROM Joke"
}