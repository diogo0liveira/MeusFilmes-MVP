package com.dao.mymovies.data.local

import androidx.paging.DataSource
import com.dao.mymovies.model.Movie
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created in 07/05/19 10:57.
 *
 * @author Diogo Oliveira.
 */
interface MovieLocalDataSource
{
    fun save(movie: Movie): Completable

    fun delete(movie: Movie): Completable

    fun isFavorite(movie: Movie): Single<Boolean>

    fun getMovies(): DataSource.Factory<Int, Movie>
}