package com.dao.mymovies.features.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dao.mymovies.MovieFactory
import com.dao.mymovies.TITLE_A
import com.dao.mymovies.TITLE_B
import com.dao.mymovies.data.repository.FakeMoviesRepository
import com.dao.mymovies.model.Movie
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Created in 08/05/19 09:00.
 *
 * @author Diogo Oliveira.
 */
class SearchMoviesPresenterTest
{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var composite: CompositeDisposable
    @Mock
    private lateinit var view: SearchMoviesInteractor.View
    @Mock
    private lateinit var observer: Observer<PagedList<Movie>>

    private lateinit var presenter: SearchMoviesPresenter
    private lateinit var repository: FakeMoviesRepository

    init
    {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)

        repository = FakeMoviesRepository()
        presenter = SearchMoviesPresenter(repository, composite)
        presenter.initialize(view)
    }

    @Test
    fun initialize()
    {
        verify(view).initializeView()
    }

    @Test
    fun `search observer empty`()
    {
        repository.movies = mutableListOf()

        presenter.searchObserver().observeForever(observer)
        assertThat(presenter.searchObserver().value.orEmpty(), `is`(emptyList()))
    }

    @Test
    fun `search observer not empty`()
    {
        val movie = MovieFactory.build(1)
        repository.movies = mutableListOf(movie)

        presenter.searchObserver().observeForever(observer)
        assertThat(presenter.searchObserver().value.orEmpty(), `is`(emptyList()))
    }

    @Test
    fun `search movies`()
    {
        val movies = mutableListOf(
                MovieFactory.build(1, TITLE_A),
                MovieFactory.build(1, TITLE_B))

        repository.movies = movies
        presenter.searchObserver().observeForever(observer)

        presenter.searchMovies("Title")
        assertThat(presenter.searchObserver().value.orEmpty(), `is`(movies.toList()))
    }

    @Test
    fun terminate()
    {
        presenter.terminate()
        verify(composite).clear()
    }
}