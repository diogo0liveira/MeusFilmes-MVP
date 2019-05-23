package com.dao.mymovies.features.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dao.mymovies.MovieFactory
import com.dao.mymovies.TITLE_A
import com.dao.mymovies.TITLE_B
import com.dao.mymovies.data.local.FakeMoviesLocalDataSource
import com.dao.mymovies.data.remote.FakeMoviesRemoteDataSource
import com.dao.mymovies.data.repository.FakeMovieRepository
import com.dao.mymovies.model.Movie
import com.dao.mymovies.model.Order
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.text.SimpleDateFormat

/**
 * Created in 03/05/19 11:20.
 *
 * @author Diogo Oliveira.
 */
class MyMoviesPresenterTest
{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var view: MyMoviesInteractor.View
    @Mock
    private lateinit var observer: Observer<PagedList<Movie>>

    private lateinit var presenter: MyMoviesPresenter
    private lateinit var repository: FakeMovieRepository

    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)

        repository = FakeMovieRepository(FakeMoviesLocalDataSource(), FakeMoviesRemoteDataSource())
        presenter = MyMoviesPresenter(repository)
        presenter.initialize(view)
    }

    @Test
    fun initialize()
    {
        verify(view).initializeView()
    }

    @Test
    fun `movies observer empty`()
    {
        presenter.moviesObserver().observeForever(observer)
        assertThat(presenter.moviesObserver().value.orEmpty(), `is`(emptyList()))
    }

    @Test
    fun `movies observer not empty`()
    {
        val movies = listOf(MovieFactory.build(1))
        repository.save(movies[0])

        presenter.moviesObserver().observeForever(observer)
        assertThat(presenter.moviesObserver().value.orEmpty(), `is`(movies))
    }

    @Test
    fun `movies order by title`()
    {
        val movie1 = MovieFactory.build(1, TITLE_B)
        val movie2 = MovieFactory.build(2, TITLE_A)

        val movies = listOf(movie1, movie2).sortedBy { movie -> movie.title }
        repository.save(movie1)
        repository.save(movie2)

        presenter.moviesOrderBy(Order.TITLE)
        presenter.moviesObserver().observeForever(observer)
        assertThat(presenter.moviesObserver().value.orEmpty(), `is`(movies))
    }

    @Test
    fun `movies order by release`()
    {
        val format = SimpleDateFormat("dd/MM/yyyy")
        val date1 = format.parse("07/12/2016")
        val date2 = format.parse("09/11/2010")

        val movie1 = MovieFactory.build(1, TITLE_A, releaseDate = date1)
        val movie2 = MovieFactory.build(2, TITLE_B, releaseDate = date2)

        val movies = listOf(movie1, movie2).sortedBy { movie -> movie.releaseDate }
        repository.save(movie1)
        repository.save(movie2)

        presenter.moviesOrderBy(Order.DATE)
        presenter.moviesObserver().observeForever(observer)
        assertThat(presenter.moviesObserver().value.orEmpty(), `is`(movies))
    }
}