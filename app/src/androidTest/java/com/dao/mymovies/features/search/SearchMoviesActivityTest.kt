package com.dao.mymovies.features.search

import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import com.dao.mymovies.MovieFactory
import com.dao.mymovies.R
import com.dao.mymovies.data.local.FakeMoviesLocalDataSource
import com.dao.mymovies.data.remote.FakeMoviesRemoteDataSource
import com.dao.mymovies.data.repository.FakeMoviesRepository
import com.dao.mymovies.features.adapter.MyMoviesAdapter
import com.dao.mymovies.util.ToastMatcher
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
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

    init
    {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
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

        scenario.onActivity { activity ->
            val remote = FakeMoviesRemoteDataSource(mutableListOf(MovieFactory.build(1)))
            val repository = FakeMoviesRepository(FakeMoviesLocalDataSource(), remote)

            val presenter = SearchMoviesPresenter(repository, CompositeDisposable())
            activity.presenter = presenter
            presenter.initialize(activity)
        }

        val matcher = isAssignableFrom(SearchView.SearchAutoComplete::class.java)
        onView(matcher).perform(typeText("Title"), closeSoftKeyboard())
        onView(matcher).perform(pressImeActionButton())

        onView(withId(R.id.search_list)).perform(actionOnItemAtPosition<MyMoviesAdapter.ViewHolder>(0, click()))

//        Intents.init()
//        intending(hasComponent(hasShortClassName(MovieDetailActivity::class.java.simpleName)))
//        Intents.release()
//        scenario.close()
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