package com.dao.mymovies.features.list

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.dao.mymovies.MovieFactory
import com.dao.mymovies.MyMoviesApplication
import com.dao.mymovies.R
import com.dao.mymovies.RepositoryFactory
import com.dao.mymovies.di.DaggerTestAppComponent
import com.dao.mymovies.features.adapter.MyMoviesAdapter
import com.dao.mymovies.features.detail.MovieDetailActivity
import com.dao.mymovies.features.search.SearchMoviesActivity
import dagger.android.AndroidInjector
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MyMoviesActivityTest
{
    @Before
    fun setUp()
    {
        RepositoryFactory.local.movies = mutableListOf()

        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MyMoviesApplication
        val builder: AndroidInjector.Factory<MyMoviesApplication> = DaggerTestAppComponent.factory()
        app.injector = builder.create(app)
        app.injector.inject(app)
    }

    @Test
    fun initializeView()
    {
        val scenario = launchActivity<MyMoviesActivity>()
        scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.movies_list)).check(matches(isDisplayed()))
    }

    @Test
    fun onCollectionChanged_empty()
    {
        val scenario = launchActivity<MyMoviesActivity>()
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.message_empty)).check(matches(isDisplayed()))
        scenario.close()
    }

    @Test
    fun onCollectionChanged_notEmpty()
    {
        RepositoryFactory.local.movies.add(MovieFactory.build(1))

        val scenario = launchActivity<MyMoviesActivity>()
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.message_empty)).check(matches(not(isDisplayed())))
        scenario.close()
    }

    @Test
    fun onMovieViewOnClick()
    {
        RepositoryFactory.local.movies.add(MovieFactory.build(1))

        val scenario = launchActivity<MyMoviesActivity>()
        scenario.moveToState(Lifecycle.State.RESUMED)

        Intents.init()
        onView(withId(R.id.movies_list)).perform(actionOnItemAtPosition<MyMoviesAdapter.ViewHolder>(0, click()))
        intended(hasComponent(MovieDetailActivity::class.java.name))
        Intents.release()
        scenario.close()
    }

    @Test
    fun startSearchMoviesActivity()
    {
        val scenario = launchActivity<MyMoviesActivity>()
        scenario.moveToState(Lifecycle.State.RESUMED)

        Intents.init()
        onView(withId(R.id.button_add)).perform(click())
        intended(hasComponent(SearchMoviesActivity::class.java.name))
        Intents.release()
        scenario.close()
    }
}