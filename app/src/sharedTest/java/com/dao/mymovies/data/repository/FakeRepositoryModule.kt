package com.dao.mymovies.data.repository

import com.dao.mymovies.data.MovieRepository
import com.dao.mymovies.data.local.FakeLocalDataSourceModule
import com.dao.mymovies.data.local.MovieLocalDataSource
import com.dao.mymovies.data.remote.FakeRemoteDataSourceModule
import com.dao.mymovies.data.remote.MovieRemoteDataSource
import dagger.Module
import dagger.Provides

/**
 * Created in 13/05/19 10:07.
 *
 * @author Diogo Oliveira.
 */
@Module(includes = [FakeLocalDataSourceModule::class, FakeRemoteDataSourceModule::class])
class FakeRepositoryModule
{
    @Provides
    fun provideFakeMovieRepository(local: MovieLocalDataSource, remote: MovieRemoteDataSource): MovieRepository
    {
        return FakeMovieRepository(local, remote)
    }
}