package com.dao.mymovies.features.list

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.dao.mymovies.MovieFactory
import com.dao.mymovies.MyMoviesApplication
import com.dao.mymovies.R
import com.dao.mymovies.data.local.FakeMoviesLocalDataSource
import com.dao.mymovies.data.remote.FakeMoviesRemoteDataSource
import com.dao.mymovies.data.repository.FakeRepositoryModule
import com.dao.mymovies.di.DaggerTestAppComponent
import com.dao.mymovies.features.adapter.MyMoviesAdapter
import com.dao.mymovies.features.detail.MovieDetailActivity
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MyMoviesActivityTest
{
//    @get:Rule
//    var activityScenarioRule = ActivityScenarioRule<MyMoviesActivity>(MyMoviesActivity::class.java)

    private lateinit var local: FakeMoviesLocalDataSource

    @Before
    fun setUp()
    {
        local = FakeMoviesLocalDataSource()
        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MyMoviesApplication
        app.injector.inject(app)

        val module = FakeRepositoryModule(local, FakeMoviesRemoteDataSource())
        DaggerTestAppComponent.factory().repositoryModule(module).inject(app)
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
        local.movies.add(MovieFactory.build(1))

        val scenario = launchActivity<MyMoviesActivity>()
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.message_empty)).check(matches(not(isDisplayed())))
        scenario.close()
    }

    @Test
    fun onMovieViewOnClick()
    {
        local.movies.add(MovieFactory.build(1))

        val scenario = launchActivity<MyMoviesActivity>()
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.movies_list)).perform(actionOnItemAtPosition<MyMoviesAdapter.ViewHolder>(0, click()))

        Intents.init()
        intending(hasComponent(hasShortClassName(MovieDetailActivity::class.java.simpleName)))
        Intents.release()
        scenario.close()
    }

//    @Test
//    fun startSearchMoviesActivity()
//    {
//        val scenario = launchActivity<MyMoviesActivity>()
//        scenario.moveToState(Lifecycle.State.RESUMED)
//
//        scenario.onActivity { activity ->
//            activity.startSearchMoviesActivity()
//        }
//
//        Intents.init()
//        intending(hasComponent(hasShortClassName(SearchMoviesActivity::class.java.simpleName)))
//        Intents.release()
//        scenario.close()
//    }
}