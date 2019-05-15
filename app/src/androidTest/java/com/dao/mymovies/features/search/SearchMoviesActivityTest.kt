package com.dao.mymovies.features.search

import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.dao.mymovies.*
import com.dao.mymovies.di.DaggerTestAppComponent
import com.dao.mymovies.features.adapter.MyMoviesAdapter
import com.dao.mymovies.features.detail.MovieDetailActivity
import com.dao.mymovies.network.NetworkState
import com.dao.mymovies.util.ToastMatcher
import dagger.android.AndroidInjector
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify

/**
 * Created in 10/05/19 14:43.
 *
 * @author Diogo Oliveira.
 */
class SearchMoviesActivityTest
{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var activityScenarioRule = activityScenarioRule<SearchMoviesActivity>()

    init
    {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Before
    fun setUp()
    {
        RepositoryFactory.remote.movies.clear()
        RepositoryFactory.remote.movies = mutableListOf(MovieFactory.build(1))

        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MyMoviesApplication
        val builder: AndroidInjector.Factory<MyMoviesApplication> = DaggerTestAppComponent.factory()
        app.injector = builder.create(app)
        app.injector.inject(app)
    }

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

        val matcher = isAssignableFrom(SearchView.SearchAutoComplete::class.java)
        onView(matcher).perform(typeText("Title"), closeSoftKeyboard())
        onView(matcher).perform(pressImeActionButton())

        Intents.init()
        onView(withId(R.id.search_list)).perform(actionOnItemAtPosition<MyMoviesAdapter.ViewHolder>(0, click()))
        intended(hasComponent(MovieDetailActivity::class.java.name))
        Intents.release()
    }

    @Test
    fun onCollectionChanged_empty()
    {
        RepositoryFactory.remote.movies.clear()

        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.message_empty)).check(matches(isDisplayed()))
    }

    @Test
    fun onCollectionChanged_notEmpty()
    {
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)

        val matcher = isAssignableFrom(SearchView.SearchAutoComplete::class.java)
        onView(matcher).perform(typeText("Title"), closeSoftKeyboard())
        onView(matcher).perform(pressImeActionButton())

        onView(withId(R.id.message_empty)).check(matches(not(isDisplayed())))
    }

    @Test
    fun networkStateObserver_running()
    {
        RepositoryFactory.remote.movies.clear()
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onActivity { activity ->
            val liveData = MutableLiveData<NetworkState>().apply { value = NetworkState.RUNNING }
            activity.networkStateObserver(liveData)
        }

        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
    }

    @Test
    fun networkStateObserver_success()
    {
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onActivity { activity ->
            val liveData = MutableLiveData<NetworkState>().apply { value = NetworkState.SUCCESS }
            activity.networkStateObserver(liveData)
        }

        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
    }

    @Test
    fun networkStateObserver_failed()
    {
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)

        val block: () -> Unit = mock()

        scenario.onActivity { activity ->
            val liveData = MutableLiveData<NetworkState>().apply { value = NetworkState.error("", block) }
            activity.networkStateObserver(liveData)
        }

        verify(block).invoke()
    }

    @Test
    fun notifyError()
    {
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onActivity { activity ->
            activity.notifyError(R.string.app_internal_no_connection) {}
        }

        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))

        onView(allOf(withId(com.google.android.material.R.id.snackbar_text),
                withText(R.string.app_internal_no_connection))).check(matches(isDisplayed()))
    }

    @Test
    fun notifyError_action()
    {
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)

        val block: () -> Unit = mock()

        scenario.onActivity { activity ->
            activity.notifyError(R.string.app_internal_no_connection, block)
        }

        onView(allOf(withId(com.google.android.material.R.id.snackbar_action))).perform(closeSoftKeyboard(), click())
        verify(block).invoke()
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
    }
}