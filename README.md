# Chuck Norris App
The Chuck Norris App provides a random selection of Chuck Norris jokes from The Internet Chuck Norris Database (ICNdb). These jokes can be added and removed from a custom favourites system within the app. Additionally, users are able to search for a random joke using a value between 1 and 520. Since the API service also provides explicit joke references, these jokes have been screened out of the app. 

![Alt text](app/docs/find_jokes_screenshot.png?raw=true "Title") ![Alt text](app/docs/favourites_screenshot.png?raw=true "Title")

# Software Development Approach
This sofware was developed using an Agile methodology

# Architecture Design
The Project follows a MVVM with Repository pattern architecture. This architecture was chosen for:
- Seperation of Concerns that provides a way to testing the architecture components in isolation and allows for the View classes to be updated without modifying the ViewModel classes.
- Resilience to configuration changes allows the ViewModel classes to store UI data that would otherwise be lost on screen rotation or activity lifecycle changes.
- Communication between fragments using a ViewModel class removes the need for fragments to communicate via an Activity using callbacks.

The View classes use data binding to communicate updates to their respective ViewModel classes. The ViewModel classes communicate with a Repository class using coroutines and receives responses using LiveData. This is then passed back to the View classes observing this LiveData. The Repository class communicates with a RESTful API using Retrofit and caches the response to a local Room database.

![Alt text](app/docs/mvvm_architecture.png?raw=true "Title") 

# Test Cases
Espresso provides End-to-End testing of the application and network calls. Test cases included: 
- Testing the HomeActivity BottomNavigationView and onItemSelected navigation components 
- Testing the HomeFragment Search and RecyclerView and Add to Favourites dialog components
- Testing the FavouritesFragment RecyclerView and Remove from Favourites dialog components

Mockito testing provides Unit testing of the ViewModel and Repository dependencies. Test cases included:
- Testing the coroutine launch functions in the JokeViewModel and the JokeRepository classes
- Testing the dependency interactions between the FavouriteJokeViewModel and JokeRepository classes
- Testing the dependency interactions between the JokeRepository and the JokeDao and JokeApiService interfaces

# Libraries Used
- Koin to provide constructor dependency injection to classes in the application
- Retrofit to provide access to the backend API endpoints
- WorkManager to retrieve random jokes from the API service in the background
- Coroutines to run API network requests on background threads
- Material Design Library to utilise TextInputLayout and TextInputEditText components
- AndroidX to provide Lifecycle and LiveData functionality to the app
- Room to store the responses from Retrofit and favourite jokes saved by the user.
- Data binding to bind the inflated layout files to instances running in the application code.
- Espresso to perform instrumentation tests on the user interface
- Mockito to mock the ViewModel and Repository classes

*Alternative Libraries*

For instrumentation testing Robolectric could be used and for unit testing the MockK would be ideal since it uses Kotlin DSL to support mocking test cases.
Volley could be used as an alternative networking library for Retrofit and lastly, Dagger Hilt could be used as an alternative dependency injection library.

# Further Improvements

- Refactor project to use Pagination 3 that has been developed with Kotlin coroutines in mind.
- Add an 'About' fragment to the Bottom Navigation View that contains information about Chuck Norris.
