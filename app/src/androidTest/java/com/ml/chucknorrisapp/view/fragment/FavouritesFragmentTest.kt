package com.ml.chucknorrisapp.view.fragment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ml.chucknorrisapp.R
import com.ml.chucknorrisapp.view.activity.HomeActivity
import com.ml.chucknorrisapp.view.adapter.JokeAdapter
import com.ml.chucknorrisapp.view.adapter.JokeAdapter.*
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavouritesFragmentTest {
    @Rule
    @JvmField
    val activityTestRule: ActivityTestRule<HomeActivity> = ActivityTestRule(HomeActivity::class.java)

    private lateinit var menuLocationNames: Map<Int, String>

    @Before
    fun setUp() {
        val activity = activityTestRule.activity
        val res = activity.resources

        menuLocationNames = mapOf(
            R.id.nav_home to res.getString(R.string.home_text),
            R.id.nav_favourites to res.getString(R.string.favourites_text)
        )
    }

    @Test
    fun testClick_recyclerViewItem() {
        //Navigate to FavouritesFragment
        onView(
            allOf(
                withText(menuLocationNames[R.id.nav_favourites]),
                isDescendantOfA(withId(R.id.navigationView)),
                isDisplayed()
            )
        )
            .perform(click())

        //Click on a RecyclerView item
        onView(withId(R.id.favourites_recyclerview))
            .check(matches(isDisplayed())) //Check if the RecyclerView is displayed
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<JokeViewHolder>(
                    0,
                    click()
                )
            )

        //Selecting Yes button on AlertDialog
        onView(withId(android.R.id.button1))
            .check(matches(withText(R.string.yes_option_text)))
            .perform(click())

        //Click on a RecyclerView item again
        onView(withId(R.id.favourites_recyclerview))
            .check(matches(isDisplayed())) //Check if the RecyclerView is displayed
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<JokeViewHolder>(
                    1,
                    click()
                )
            )

        //Selecting No button on AlertDialog
        onView(withId(android.R.id.button2))
            .check(matches(withText(R.string.no_option_text)))
            .perform(click())
    }
}