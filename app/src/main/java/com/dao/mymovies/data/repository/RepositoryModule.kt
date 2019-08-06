package com.dao.mymovies.data.repository

import com.dao.mymovies.data.MovieRepository
import com.dao.mymovies.data.local.LocalDataSourceModule
import com.dao.mymovies.data.local.MovieLocalDataSource
import com.dao.mymovies.data.remote.MovieRemoteDataSource
import com.dao.mymovies.data.remote.RemoteDataSourceModule
import dagger.Module
import dagger.Provides

/**
 * Created in 07/05/19 11:45.
 *
 * @author Diogo Oliveira.
 */
@Module(includes = [LocalDataSourceModule::class, RemoteDataSourceModule::class])
class RepositoryModule
{
    @Provides
    fun provideMovieRepository(local: MovieLocalDataSource, remote: MovieRemoteDataSource): MovieRepository
    {
        return IMoviesRepository(local, remote)
    }
}