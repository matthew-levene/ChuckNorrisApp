package com.ml.chucknorrisapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ml.chucknorrisapp.R
import com.ml.chucknorrisapp.view.fragment.FavouritesFragment
import com.ml.chucknorrisapp.view.fragment.HomeFragment

/**
 * Class is used as the entry point for the application
 */
class HomeActivity : AppCompatActivity() {

    /**
     * Declare an item selected listener to listen for click events on the Bottom Navigation View
     */
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.nav_home -> {
                val homeFragment = HomeFragment()
                loadFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_favourites -> {
                val favouriteFragment = FavouritesFragment()
                loadFragment(favouriteFragment)
                return@OnNavigationItemSelectedListener true }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        loadBottomNavigation()
        loadFragment(HomeFragment())
    }

    /**
     * Function loads the Bottom Navigation Bar
     */
    private fun loadBottomNavigation(){
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    /**
     * Function is used to open a load a fragment into a FrameLayout
     * @param fragment
     */
    private fun loadFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_frame, fragment)
            .commit()

    }
}