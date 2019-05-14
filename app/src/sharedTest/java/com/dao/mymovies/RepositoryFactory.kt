package com.dao.mymovies

import com.dao.mymovies.data.local.FakeMoviesLocalDataSource
import com.dao.mymovies.data.remote.FakeMoviesRemoteDataSource
import com.dao.mymovies.data.repository.FakeMovieRepository

/**
 * Created in 14/05/19 14:37.
 *
 * @author Diogo Oliveira.
 */
object RepositoryFactory
{
    var local = FakeMoviesLocalDataSource()
    var remote = FakeMoviesRemoteDataSource()
    var repository = FakeMovieRepository(local, remote)
}