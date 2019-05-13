package com.dao.mymovies.features.detail

import android.os.Bundle
import android.widget.Toast
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dao.mymovies.Extras.MOVIE
import com.dao.mymovies.MovieFactory
import com.dao.mymovies.R
import com.dao.mymovies.data.local.FakeMoviesLocalDataSource
import com.dao.mymovies.data.remote.FakeMoviesRemoteDataSource
import com.dao.mymovies.data.repository.FakeMovieRepository
import com.dao.mymovies.model.Movie
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
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
    private lateinit var composite: CompositeDisposable
    @Mock
    private lateinit var view: MovieDetailInteractor.View

    private lateinit var presenter: MovieDetailPresenter
    private lateinit var repository: FakeMovieRepository

    init
    {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)

        repository = FakeMovieRepository(FakeMoviesLocalDataSource(), FakeMoviesRemoteDataSource())
        presenter = MovieDetailPresenter(repository, composite)
        presenter.initialize(view)
    }

    @Test
    fun initialize()
    {
        verify(view).initializeView()
    }

    @Test
    fun `save instance state`()
    {
        val movie = MovieFactory.build(1)
        val bundle = mock(Bundle::class.java)

        doReturn(movie).`when`(bundle).getParcelable<Movie>(MOVIE)
        presenter.onRestoreInstanceState(bundle, false)

        presenter.onSaveInstanceState(bundle)
        verify(bundle).putParcelable(MOVIE, movie)
    }

    @Test
    fun `restore instance state`()
    {
        val movie = MovieFactory.build(1)
        val bundle = mock(Bundle::class.java)

        doReturn(movie).`when`(bundle).getParcelable<Movie>(MOVIE)
        presenter.onRestoreInstanceState(bundle, false)
        verify(view).putOnForm(movie)
    }

    @Test
    fun `restore instance state empty`()
    {
        val bundle = null
        presenter.onRestoreInstanceState(bundle, false)
        verify(view).showToast(R.string.movie_detail_not_found, Toast.LENGTH_LONG)
    }

    @Test
    fun `movie action is favorite`()
    {
        val movie = MovieFactory.build(1)
        movie.isFavorite.set(false)

        val bundle = mock(Bundle::class.java)
        repository.save(movie)

        doReturn(movie).`when`(bundle).getParcelable<Movie>(MOVIE)
        presenter.onRestoreInstanceState(bundle, false)
        presenter.movieAction()

        repository.isFavorite(movie)
                .test()
                .assertValue(true)
    }

    @Test
    fun `movie action not is favorite`()
    {
        val movie = MovieFactory.build(1)
        movie.isFavorite.set(true)

        val bundle = mock(Bundle::class.java)
        doReturn(movie).`when`(bundle).getParcelable<Movie>(MOVIE)

        presenter.onRestoreInstanceState(bundle, false)
        presenter.movieAction()

        repository.isFavorite(movie)
                .test()
                .assertValue(false)
    }

    @Test
    fun terminate()
    {
        presenter.terminate()
        verify(composite).clear()
    }
}