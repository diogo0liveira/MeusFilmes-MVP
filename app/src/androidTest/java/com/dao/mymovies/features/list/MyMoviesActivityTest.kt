package com.dao.mymovies.features.list

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.dao.mymovies.MovieFactory
import com.dao.mymovies.R
import com.dao.mymovies.features.detail.MovieDetailActivity
import com.dao.mymovies.features.search.SearchMoviesActivity
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MyMoviesActivityTest
{
    @get:Rule
    var activityScenarioRule = activityScenarioRule<MyMoviesActivity>()

    @Test
    fun initializeView()
    {
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.message_empty)).check(matches(isDisplayed()))
    }

    @Test
    fun onCollectionChanged()
    {
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onActivity { activity ->
            activity.onCollectionChanged(false)
        }

        onView(withId(R.id.message_empty)).check(matches(not(isDisplayed())))
        scenario.close()
    }

    @Test
    fun onMovieViewOnClick()
    {
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onActivity { activity ->
            activity.onMovieViewOnClick(MovieFactory.build(1))
        }

        Intents.init()
        intending(hasComponent(hasShortClassName(MovieDetailActivity::class.java.simpleName)))
        Intents.release()
        scenario.close()
    }

    @Test
    fun startSearchMoviesActivity()
    {
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onActivity { activity ->
            activity.startSearchMoviesActivity()
        }

        Intents.init()
        intending(hasComponent(hasShortClassName(SearchMoviesActivity::class.java.simpleName)))
        Intents.release()
        scenario.close()
    }
}