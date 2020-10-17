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
import junit.framework.Assert.*
import org.hamcrest.Matchers.allOf
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

    private val menuLocationIds = intArrayOf(R.id.nav_home, R.id.nav_favourites)

    private var menuLocationNames: Map<Int, String>? = null

    private var bottomNavigation: BottomNavigationView? = null

    @Before
    fun setUp() {
        val activity = activityTestRule.activity
        bottomNavigation = activity.findViewById(R.id.navigationView)
        val res = activity.resources

        menuLocationNames = mapOf(
            R.id.nav_home to res.getString(R.string.home_text),
            R.id.nav_favourites to res.getString(R.string.favourites_text)
        )
    }

    @Test
    fun testBottomNav_isDisplayedSuccessfully(){
        onView(withId(R.id.navigationView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testBottomNav_checkMenuContents(){
        val menu = bottomNavigation!!.menu

        assertNotNull("Menu should not be null", menu)
        assertEquals("Should have matching number of items", menuLocationIds.size, menu.size())

        for(value in menuLocationIds.indices){
            assertEquals("Should have matching IDs", menuLocationIds[value], menu.getItem(value).itemId)
        }
    }

    @Test
    fun testNavigationSelectionListener_clickHomeFragment(){
        //Arrange
        val mockedListener = mock(BottomNavigationView.OnNavigationItemSelectedListener::class.java)
        bottomNavigation?.setOnNavigationItemSelectedListener(mockedListener)

        `when`(mockedListener.onNavigationItemSelected(any(MenuItem::class.java))).thenReturn(true)

        //Act
        onView(
            allOf(withText(menuLocationNames?.get(R.id.nav_home)),
                isDescendantOfA(withId(R.id.navigationView)),
                isDisplayed()
            ))
            .perform(click())

        //Verify that the listener is aware a click occurred
        bottomNavigation?.menu?.findItem(R.id.nav_home)?.let {
            verify(mockedListener).onNavigationItemSelected(it)
        }

        //Assert that the menu item is now selected
        bottomNavigation?.menu?.findItem(R.id.nav_home)?.isChecked?.let { assertTrue(it) };
    }

    @Test
    fun testNavigationSelectionListener_clickFavouriteFragment(){
        //Arrange
        val mockedListener = mock(BottomNavigationView.OnNavigationItemSelectedListener::class.java)
        bottomNavigation?.setOnNavigationItemSelectedListener(mockedListener)

        `when`(mockedListener.onNavigationItemSelected(any(MenuItem::class.java))).thenReturn(true)

        //Act
        onView(
            allOf(withText(menuLocationNames?.get(R.id.nav_favourites)),
                isDescendantOfA(withId(R.id.navigationView)),
                isDisplayed()
            ))
            .perform(click())

        //Verify that the listener is aware a click occurred
        bottomNavigation?.menu?.findItem(R.id.nav_favourites)?.let {
            verify(mockedListener, times(1)).onNavigationItemSelected(it)
        }

        //Assert that the menu item is now selected
        bottomNavigation?.menu?.findItem(R.id.nav_favourites)?.isChecked?.let { assertTrue(it) };
    }
}