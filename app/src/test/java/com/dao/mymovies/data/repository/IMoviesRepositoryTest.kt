package com.dao.mymovies.data.repository

import com.dao.mymovies.data.remote.MovieRemoteDataSource
import com.dao.mymovies.data.local.FakeMoviesLocalDataSource
import com.dao.mymovies.data.local.MovieLocalDataSource
import com.dao.mymovies.data.remote.FakeMoviesRemoteDataSource
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created in 07/05/19 10:45.
 *
 * @author Diogo Oliveira.
 */
class IMoviesRepositoryTest
{
    private lateinit var repository: IMoviesRepository
    private lateinit var local: MovieLocalDataSource
    private lateinit var remote: MovieRemoteDataSource

    @Before
    fun setUp()
    {
        local = FakeMoviesLocalDataSource(mutableListOf())
        remote = FakeMoviesRemoteDataSource(mutableListOf())
        repository = IMoviesRepository(local = local, remote = remote)
    }

    @After
    fun tearDown()
    {
    }

    @Test
    fun save()
    {
    }

    @Test
    fun delete()
    {
    }

    @Test
    fun getMovies()
    {
    }

    @Test
    fun isFavorite()
    {
    }

    @Test
    fun search()
    {
    }
}