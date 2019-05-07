package com.dao.mymovies.data.local

import androidx.paging.DataSource
import com.dao.mymovies.data.local.dao.MovieDAO
import com.dao.mymovies.model.Movie
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created in 24/04/19 11:44.
 *
 * @author Diogo Oliveira.
 */
@Singleton
class IMoviesLocalDataSource @Inject constructor(private val dataSource: MovieDAO) : MovieLocalDataSource
{
    override fun save(movie: Movie): Completable = dataSource.insert(movie)

    override fun delete(movie: Movie): Completable = dataSource.delete(movie)

    override fun isFavorite(movie: Movie): Single<Boolean> = dataSource.isFavorite(movie.id)

    override fun getMovies(): DataSource.Factory<Int, Movie> = dataSource.getAll()
}