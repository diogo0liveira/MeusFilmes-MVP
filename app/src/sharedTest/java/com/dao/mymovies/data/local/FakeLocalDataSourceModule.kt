package com.dao.mymovies.data.local

import dagger.Binds
import dagger.Module

/**
 * Created in 13/05/19 10:07.
 *
 * @author Diogo Oliveira.
 */
@Module
interface FakeLocalDataSourceModule
{
    @Binds
    fun provideFakeMovieLocalDataSource(dataSource: FakeMoviesLocalDataSource): MovieLocalDataSource
}
