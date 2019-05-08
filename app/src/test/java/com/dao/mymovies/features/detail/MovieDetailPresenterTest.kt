package com.dao.mymovies.features.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dao.mymovies.data.repository.FakeMoviesRepository
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Created in 08/05/19 15:41.
 *
 * @author Diogo Oliveira.
 */
class MovieDetailPresenterTest
{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var view: MovieDetailInteractor.View

    private lateinit var presenter: MovieDetailPresenter
    private lateinit var repository: FakeMoviesRepository

    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)

        repository = FakeMoviesRepository()
        presenter = MovieDetailPresenter(repository, CompositeDisposable())
        presenter.initialize(view)
    }

    @Test
    fun initialize()
    {
        verify(view).initializeView()
    }

//    @Test
//    fun `save instance state`()
//    {
//        val movie = MovieFactory.build(1, releaseDate = Date(), cover = "")
//
//        var bundle = Bundle()
//        bundle.putParcelable(MOVIE, movie)
//        presenter.onRestoreInstanceState(bundle, false)
//
//        bundle = Bundle()
//        presenter.onSaveInstanceState(bundle)
//        assertThat(bundle.getParcelable(MOVIE) as Movie, `is`(movie))
//    }

//    @Test
//    fun `restore instance state`()
//    {
//        val movie = MovieFactory.build(1)
//
//        val bundle = Bundle()
//        bundle.putParcelable(MOVIE, movie)
//        presenter.onRestoreInstanceState(bundle, false)
//        verify(view).putOnForm(movie)
//    }
//
//    @Test
//    fun `movie action`()
//    {
//        val movie = MovieFactory.build(1)
//
//        val bundle = Bundle()
//        bundle.putParcelable(MOVIE, movie)
//        presenter.onRestoreInstanceState(bundle, false)
//
//        presenter.movieAction()
//        verify(repository).isFavorite(movie)
//    }
//
//    @Test
//    fun terminate()
//    {
//        val composite = CompositeDisposable()
//        presenter.terminate()
//        verify(composite).clear()
//    }
}