package com.ml.chucknorrisapp.view.activity

import android.view.MenuItem
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ml.chucknorrisapp.R
import com.ml.chucknorrisapp.R.*
import com.ml.chucknorrisapp.R.id.*
import junit.framework.Assert.*
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule
    @JvmField
    val activityTestRule: ActivityTestRule<HomeActivity> = ActivityTestRule(HomeActivity::class.java)

    private val menuLocationIds = intArrayOf(nav_home, nav_favourites)

    private lateinit var menuLocationNames: Map<Int, String>

    private lateinit var bottomNavigation:BottomNavigationView

    @Before
    fun setUp() {
        val activity = activityTestRule.activity
        bottomNavigation = activity.findViewById(navigationView)
        val res = activity.resources

        menuLocationNames = mapOf(
            nav_home to res.getString(string.home_text),
            nav_favourites to res.getString(string.favourites_text)
        )
    }

    @Test
    fun testBottomNav_checkMenuContents(){
        val menu = bottomNavigation.menu

        assertNotNull("Menu should not be null", menu)
        assertEquals("Should have matching number of items", menuLocationIds.size, menu.size())

        for(value in menuLocationIds.indices){
            assertEquals("Should have matching IDs", menuLocationIds[value], menu.getItem(value).itemId)
        }
    }

    @Test
    fun testBottomNav_isDisplayedSuccessfully(){
        onView(withId(navigationView))
            .check(matches(isDisplayed()))
    }


    @Test
    fun testNavigationSelectionListener_clickHomeFragment(){
        //Arrange
        val mockedListener = mock(BottomNavigationView.OnNavigationItemSelectedListener::class.java)
        bottomNavigation.setOnNavigationItemSelectedListener(mockedListener)

        `when`(mockedListener.onNavigationItemSelected(any(MenuItem::class.java))).thenReturn(true)

        //Act
        onView(
            allOf(withText(menuLocationNames[nav_home]),
                isDescendantOfA(withId(navigationView)),
                isDisplayed()
            ))
            .perform(click())

        //Assert
        verify(mockedListener)
            .onNavigationItemSelected(
                bottomNavigation.menu.findItem(nav_home)
            )

        assertTrue(bottomNavigation.menu.findItem(nav_home).isChecked)
    }

    @Test
    fun testNavigationSelectionListener_clickFavouriteFragment(){
        //Arrange
        val mockedListener = mock(BottomNavigationView.OnNavigationItemSelectedListener::class.java)
        bottomNavigation.setOnNavigationItemSelectedListener(mockedListener)

        `when`(mockedListener.onNavigationItemSelected(any(MenuItem::class.java))).thenReturn(true)

        //Act
        onView(
            allOf(withText(menuLocationNames[nav_favourites]),
                isDescendantOfA(withId(navigationView)),
                isDisplayed()
            ))
            .perform(click())

        //Assert
        verify(mockedListener)
            .onNavigationItemSelected(
                bottomNavigation.menu.findItem(nav_favourites)
            )

        assertTrue(bottomNavigation.menu.findItem(nav_favourites).isChecked)
    }
}