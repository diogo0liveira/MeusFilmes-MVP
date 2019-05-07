package com.dao.mymovies.features.list

import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dao.mymovies.data.repository.FakeMoviesRepository
import com.dao.mymovies.model.Movie
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Created in 03/05/19 11:20.
 *
 * @author Diogo Oliveira.
 */
class MyMoviesPresenterTest
{
    @Mock
    private lateinit var view: MyMoviesInteractor.View
    private lateinit var presenter: MyMoviesPresenter

    private lateinit var repository: FakeMoviesRepository

    @Mock
    private lateinit var observer: Observer<PagedList<Movie>>

    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)

        repository = FakeMoviesRepository()
        presenter = MyMoviesPresenter(repository)
        presenter.initialize(view)
    }

    @Test
    fun initialize()
    {
        verify(view).initializeView()
    }

    @Test
    fun `movies observer`()
    {
        presenter.moviesObserver().observeForever(observer)
        assertThat(presenter.moviesObserver().value.orEmpty(), `is`(emptyList()))
    }

//    @Test
//    fun `movies orderBy`()
//    {
//    }
}