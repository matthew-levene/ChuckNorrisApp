package com.ml.chucknorrisapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ml.chucknorrisapp.R
import com.ml.chucknorrisapp.view.fragment.DisplayJokeFragment
import com.ml.chucknorrisapp.view.fragment.SearchJokeFragment

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
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_favourites -> {

                return@OnNavigationItemSelectedListener true }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        loadFragments()
        loadBottomNavigation()
    }

    /**
     * Function loads SearchJokeFragment and DisplayJokeFragment into FrameLayouts
     */
    private fun loadFragments(){
        supportFragmentManager
            .beginTransaction()
            .add(R.id.search_frame, SearchJokeFragment())
            .add(R.id.display_frame, DisplayJokeFragment())
            .commit()
    }

    /**
     * Function loads the Bottom Navigation Bar
     */
    private fun loadBottomNavigation(){
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }


}