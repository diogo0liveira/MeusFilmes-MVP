@file:Suppress("unused")

package com.dao.mymovies.data.local

import dagger.Binds
import dagger.Module

/**
 * Created in 07/05/19 11:47.
 *
 * @author Diogo Oliveira.
 */
@Module
interface LocalDataSourceModule
{
    @Binds
    fun provideMovieLocalDataSource(dataSource: IMoviesLocalDataSource): MovieLocalDataSource
}