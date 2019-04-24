package com.dao.mymovies.data.local

import androidx.paging.DataSource
import com.dao.mymovies.data.local.dao.MovieDAO
import com.dao.mymovies.model.Movie
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created in 24/04/19 11:44.
 *
 * @author Diogo Oliveira.
 */
@Singleton
class MoviesLocalDataSource @Inject constructor(private val dataSource: MovieDAO)
{
    fun save(movie: Movie): Completable = dataSource.insert(movie)

    fun delete(movie: Movie): Completable = dataSource.delete(movie)

    fun isFavorite(movie: Movie): Flowable<Boolean> = dataSource.isFavorite(movie.id)

    fun search(): DataSource.Factory<Int, Movie> = dataSource.getAll()
}