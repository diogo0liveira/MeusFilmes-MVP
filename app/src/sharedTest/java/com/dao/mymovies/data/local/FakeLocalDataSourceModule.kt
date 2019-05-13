package com.dao.mymovies.data.local

import com.dao.mymovies.model.Movie
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Created in 13/05/19 10:07.
 *
 * @author Diogo Oliveira.
 */
//@Module
//interface FakeLocalDataSourceModule
//{
//    @Binds
//    fun provideFakeMovieLocalDataSource(dataSource: FakeMoviesLocalDataSource): MovieLocalDataSource
//}


@Module
class FakeLocalDataSourceModule(private val movies: MutableList<Movie>)
{
    @Provides
    fun provideFakeMovieLocalDataSource(dataSource: FakeMoviesLocalDataSource): MovieLocalDataSource
    {
        dataSource.movies = movies
        return dataSource
    }
}