package com.dao.mymovies.features.detail

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.dao.mymovies.Extras.MOVIE
import com.dao.mymovies.MovieFactory
import com.dao.mymovies.R
import com.dao.mymovies.util.DateTime
import com.dao.mymovies.util.RatingBarMatcher
import com.dao.mymovies.util.ToastMatcher
import com.dao.mymovies.util.ToolbarMatcher
import org.hamcrest.core.Is.`is`
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@LargeTest
@RunWith(AndroidJUnit4::class)
class MovieDetailActivityTest
{
    private val movie = MovieFactory.build(1, releaseDate = Date())

    private val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext,
            MovieDetailActivity::class.java).putExtra(MOVIE, movie)

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MovieDetailActivity>(intent)

    @Test
    fun initializeView()
    {
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.cover)).check(matches(isDisplayed()))
        onView(withId(R.id.button_favorite)).check(matches(isDisplayed()))

        onView(withId(R.id.ratingBar)).check(matches(isDisplayed()))
        onView(withId(R.id.releaseDate)).check(matches(isDisplayed()))
        onView(withId(R.id.overView)).check(matches(isDisplayed()))
    }

    @Test
    fun putOnForm()
    {
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val releaseDate = context.getString(R.string.movie_detail_release_date, DateTime.dateFormatLong(movie.releaseDate!!))

        onView(withId(R.id.toolbar)).check(matches(ToolbarMatcher(`is`(movie.title))))
        onView(withId(R.id.overView)).check(matches(withText(movie.overView)))
        onView(withId(R.id.releaseDate)).check(matches(withText(releaseDate)))
        onView(withId(R.id.ratingBar)).check(matches(RatingBarMatcher(`is`(movie.voteAverage.div(2)))))
        scenario.close()
    }

    @Test
    fun changeMovieFavoriteSuccess()
    {
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.button_favorite)).perform(click())
        assertThat(movie.isFavorite.get(), `is`(false))
        scenario.close()
    }

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