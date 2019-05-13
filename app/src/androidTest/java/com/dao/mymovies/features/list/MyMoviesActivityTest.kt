package com.dao.mymovies.features.list

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.dao.mymovies.MyMoviesApplication
import com.dao.mymovies.R
import com.dao.mymovies.data.local.FakeMoviesLocalDataSource
import com.dao.mymovies.data.remote.FakeMoviesRemoteDataSource
import com.dao.mymovies.data.repository.FakeMovieRepository
import dagger.android.AndroidInjector
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MyMoviesActivityTest
{
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule<MyMoviesActivity>(MyMoviesActivity::class.java)

    @Before
    fun setUp()
    {
        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MyMoviesApplication
        val builder: AndroidInjector.Factory<MyMoviesApplication> = DaggerTestAppComponent.factory()
        app.injector = builder.create(app)
//        app.injector.inject(app)
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

        scenario.onActivity { activity ->
            val local = FakeMoviesLocalDataSource()
            local.movies.clear()

            val repository = FakeMovieRepository(local, FakeMoviesRemoteDataSource())
            activity.presenter = MyMoviesPresenter(repository)
            activity.presenter.initialize(activity)

            activity.presenter.moviesObserver().observeForever {  }
        }

        onView(withId(R.id.message_empty)).check(matches(isDisplayed()))
        scenario.close()
    }

//    @Test
//    fun onCollectionChanged_notEmpty()
//    {
//        val scenario = activityScenarioRule.scenario
//        scenario.moveToState(Lifecycle.State.RESUMED)
//
//        scenario.onActivity { activity ->
//            activity.onCollectionChanged(false)
//        }
//
//        onView(withId(R.id.message_empty)).check(matches(not(isDisplayed())))
//        scenario.close()
//    }
//
//    @Test
//    fun onMovieViewOnClick()
//    {
//        val scenario = activityScenarioRule.scenario
//        scenario.moveToState(Lifecycle.State.RESUMED)
//
//        scenario.onActivity { activity ->
//            activity.onMovieViewOnClick(MovieFactory.build(1))
//        }
//
//        Intents.init()
//        intending(hasComponent(hasShortClassName(MovieDetailActivity::class.java.simpleName)))
//        Intents.release()
//        scenario.close()
//    }
//
//    @Test
//    fun startSearchMoviesActivity()
//    {
//        val scenario = activityScenarioRule.scenario
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