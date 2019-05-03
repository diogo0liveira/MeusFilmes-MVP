package com.dao.mymovies.data.repository

import androidx.paging.DataSource
import com.dao.mymovies.data.local.MoviesLocalDataSource
import com.dao.mymovies.data.remote.MoviesRemoteDataSource
import com.dao.mymovies.model.Movie
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

/**
 * Created in 03/05/19 09:44.
 *
 * @author Diogo Oliveira.
 */
class MoviesRepositoryTest
{
    @Mock
    private lateinit var local: MoviesLocalDataSource
    @Mock
    private lateinit var remote: MoviesRemoteDataSource

    private lateinit var repository: MoviesRepository
    private lateinit var movie: Movie

    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)
        repository = MoviesRepository(local, remote)
        movie = mock(Movie::class.java)
    }

    @Test
    fun `save success`()
    {
        doReturn(Completable.complete()).`when`(local).save(movie)

        repository.save(movie)
                .test()
                .assertComplete()
    }

    @Test
    fun `delete success`()
    {
        doReturn(Completable.complete()).`when`(local).delete(movie)

        repository.delete(movie)
                .test()
                .assertComplete()
    }

    @Test
    fun `load movies`()
    {
        val dataSourceFactory = mock(DataSource.Factory::class.java)
        doReturn(dataSourceFactory).`when`(local).loadMovies()

        assertThat(repository.loadMovies(), `is`(dataSourceFactory))
    }

    @Test
    fun `is favorite`()
    {
        doReturn(Single.just(true)).`when`(local).isFavorite(movie)

        repository.isFavorite(movie)
                .test()
                .assertValue(true)
    }

    @Test
    fun search()
    {
        val response = mock(Response::class.java)
        doReturn(Observable.just(response)).`when`(remote).search("")

        repository.search("")
                .test()
                .assertValue { it == response }
    }}