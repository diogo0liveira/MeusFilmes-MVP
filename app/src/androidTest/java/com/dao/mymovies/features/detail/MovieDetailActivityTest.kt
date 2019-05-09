package com.dao.mymovies.features.detail

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.dao.mymovies.MovieFactory
import com.dao.mymovies.R
import org.hamcrest.CoreMatchers.`is`
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MovieDetailActivityTest
{
    @get:Rule
    var activityScenarioRule = activityScenarioRule<MovieDetailActivity>()

    @Test
    fun initializeView()
    {
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)
        Espresso.onView(withId(R.id.message_empty)).check(matches(isDisplayed()))
    }

    @Test
    fun putOnForm()
    {
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)
        val movie = MovieFactory.build(1)

        scenario.onActivity { activity ->
            activity.putOnForm(movie)
        }

        onView(withId(R.id.toolbar)).check(matches(withText(movie.title)))
        onView(withId(R.id.overView)).check(matches(withText(movie.overView)))
        scenario.close()
    }

    @Test
    fun changeMovieFavoriteSuccess()
    {
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)
        val movie = MovieFactory.build(1)

        scenario.onActivity { activity ->
            activity.putOnForm(movie)
        }

        onView(withId(R.id.button_favorite)).perform(click())
        assertThat(movie.isFavorite.get(), `is`(true))
//        onView(withId(R.id.button_favorite)).check(matches(withR.drawable.abc_ab_share_pack_mtrl_alpha)))
        scenario.close()
    }

//    @Test
//    fun showToast()
//    {
//        val scenario = activityScenarioRule.scenario
//        scenario.moveToState(Lifecycle.State.RESUMED)
//
//        scenario.onActivity { activity ->
//            activity.showToast(R.string.app_name, Toast.LENGTH_SHORT)
//            onView(withText(R.string.app_name)).inRoot(withDecorView(not(`is`(activity.window.decorView)))).check(matches(isDisplayed()))
//        }
//
//        scenario.close()
//    }
}