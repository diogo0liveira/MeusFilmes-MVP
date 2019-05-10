package com.dao.mymovies.features.search

import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import com.dao.mymovies.MovieFactory
import com.dao.mymovies.R
import com.dao.mymovies.features.detail.MovieDetailActivity
import com.dao.mymovies.util.ToastMatcher
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test

/**
 * Created in 10/05/19 14:43.
 *
 * @author Diogo Oliveira.
 */
class SearchMoviesActivityTest
{
    @get:Rule
    var activityScenarioRule = activityScenarioRule<SearchMoviesActivity>()

    @Test
    fun initializeView()
    {
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.search_list)).check(matches(isDisplayed()))
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
    fun onCollectionChanged_empty()
    {
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onActivity { activity ->
            activity.onCollectionChanged(true)
        }

        onView(withId(R.id.message_empty)).check(matches(isDisplayed()))
        scenario.close()
    }

    @Test
    fun onCollectionChanged_notEmpty()
    {
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onActivity { activity ->
            activity.onCollectionChanged(false)
        }

        onView(withId(R.id.message_empty)).check(matches(not(isDisplayed())))
        scenario.close()
    }
//
//    @Test
//    fun executeRequireNetwork()
//    {
//    }
//
//    @Test
//    fun networkStateObserver()
//    {
//    }
//
//    @Test
//    fun notifyError()
//    {
//    }

    @Test
    fun showToast()
    {
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onActivity { activity ->
            activity.showToast(R.string.app_internal_error_client, Toast.LENGTH_LONG)
        }

        onView(withText(R.string.app_internal_error_client)).inRoot(ToastMatcher()).check(matches(isDisplayed()))
        scenario.close()
    }
}