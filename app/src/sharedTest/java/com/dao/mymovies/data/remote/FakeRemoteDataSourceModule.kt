package com.dao.mymovies.data.remote

import dagger.Binds
import dagger.Module

/**
 * Created in 13/05/19 10:08.
 *
 * @author Diogo Oliveira.
 */
@Module
interface FakeRemoteDataSourceModule
{
    @Binds
    fun provideFakeMovieRemoteDataSource(dataSource: FakeMoviesRemoteDataSource): MovieRemoteDataSource
}