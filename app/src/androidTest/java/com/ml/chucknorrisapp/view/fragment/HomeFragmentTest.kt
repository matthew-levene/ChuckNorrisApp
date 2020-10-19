package com.ml.chucknorrisapp.view.fragment

import android.provider.Settings.Global.getString
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.ml.chucknorrisapp.R
import com.ml.chucknorrisapp.view.activity.HomeActivity
import com.ml.chucknorrisapp.view.adapter.JokeAdapter.*
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @Rule
    @JvmField
    val activityTestRule: ActivityTestRule<HomeActivity> = ActivityTestRule(HomeActivity::class.java)

    private var menuLocationNames: Map<Int, String>? = null

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

        onView(
            Matchers.allOf(
                withText(menuLocationNames?.get(R.id.nav_home)),
                isDescendantOfA(withId(R.id.navigationView)),
                isDisplayed()
            )
        )
            .perform(click())

        //Click on a RecyclerView item
        onView(withId(R.id.joke_display_recyclerview))
            .check(matches(isDisplayed())) //Check if the RecyclerView is displayed
            .perform(
                actionOnItemAtPosition<JokeViewHolder>(
                    0,
                    click()
                )
            )

        //Selecting Yes button on AlertDialog
        onView(withId(android.R.id.button1))
            .check(matches(withText(R.string.yes_option_text)))
            .perform(click())

        //Click on a RecyclerView item again
        onView(withId(R.id.joke_display_recyclerview))
            .check(matches(isDisplayed())) //Check if the RecyclerView is displayed
            .perform(
                actionOnItemAtPosition<JokeViewHolder>(
                    5,
                    click()
                )
            )

        //Selecting No button on AlertDialog
        onView(withId(android.R.id.button2))
            .check(matches(withText(R.string.no_option_text)))
            .perform(click())
    }

    @Test
    fun testClick_searchSpecificJoke(){
        onView(withId(R.id.search_input_field))
            .perform(typeText("2"))

        onView(withId(R.id.search_button))
            .perform(click())
    }

    @Test
    fun testClick_searchSpecificJokeInvalidInput(){
        onView(withId(R.id.search_input_field))
            .perform(typeText("Test"))

        onView(withId(R.id.search_button))
            .perform(click())

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.please_enter_valid_num_1_and_520)))
    }

    @Test
    fun testClick_searchSpecificJokeNoInput(){
        onView(withId(R.id.search_button))
            .perform(click())

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.please_enter_valid_num_1_and_520)))
    }

    @Test
    fun testClick_searchRandomJoke(){
        onView(withId(R.id.random_button))
            .perform(click())

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.matches_retrieving_text)))
    }
}