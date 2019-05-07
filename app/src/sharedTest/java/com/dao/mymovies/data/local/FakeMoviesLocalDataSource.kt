package com.dao.mymovies.data.local

import androidx.paging.DataSource
import com.dao.mymovies.model.Movie
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created in 07/05/19 10:30.
 *
 * @author Diogo Oliveira.
 */
class FakeMoviesLocalDataSource(private val movies: MutableList<Movie>): MovieLocalDataSource
{
    override fun save(movie: Movie): Completable
    {
        movies.add(movie)
        return Completable.complete()
    }

    override fun delete(movie: Movie): Completable
    {
        movies.remove(movie)
        return Completable.complete()
    }

    override fun isFavorite(movie: Movie): Single<Boolean>
    {
        return Single.just(movie.isFavorite.get())
    }

    override fun getMovies(): DataSource.Factory<Int, Movie>
    {
        return null!!
    }
}