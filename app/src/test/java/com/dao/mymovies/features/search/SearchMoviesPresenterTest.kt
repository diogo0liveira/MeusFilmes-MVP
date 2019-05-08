package com.dao.mymovies.features.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dao.mymovies.data.repository.FakeMoviesRepository
import com.dao.mymovies.model.Movie
import io.reactivex.disposables.CompositeDisposable
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
    private lateinit var view: SearchMoviesInteractor.View
    private lateinit var presenter: SearchMoviesPresenter

    private lateinit var repository: FakeMoviesRepository

    @Mock
    private lateinit var observer: Observer<PagedList<Movie>>

    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)

        repository = FakeMoviesRepository()
        presenter = SearchMoviesPresenter(repository, CompositeDisposable())
        presenter.initialize(view)
    }

    @Test
    fun initialize()
    {
        verify(view).initializeView()
    }

    @Test
    fun `search observer`()
    {
        presenter.searchObserver().observeForever(observer)
        assertThat(presenter.searchObserver().value.orEmpty(), `is`(emptyList()))
    }

    @Test
    fun `search movies`()
    {

    }

    @Test
    fun terminate()
    {

    }
}