package com.ml.chucknorrisapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import androidx.fragment.app.Fragment
import com.ml.chucknorrisapp.R
import com.ml.chucknorrisapp.view.fragment.DisplayJokeFragment
import com.ml.chucknorrisapp.view.fragment.SearchJokeFragment

/**
 * Class is used as the entry point for the application
 */
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        loadFragments()
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
}