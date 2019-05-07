@file:Suppress("unused")

package com.dao.mymovies.data.remote

import dagger.Binds
import dagger.Module

/**
 * Created in 07/05/19 11:47.
 *
 * @author Diogo Oliveira.
 */
@Module
interface RemoteDataSourceModule
{
    @Binds
    fun provideMovieRemoteDataSource(dataSource: IMoviesRemoteDataSource): MovieRemoteDataSource
}