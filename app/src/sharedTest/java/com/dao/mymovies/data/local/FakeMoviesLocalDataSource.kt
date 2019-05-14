package com.dao.mymovies.data.local

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.dao.mymovies.model.Movie
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created in 07/05/19 10:30.
 *
 * @author Diogo Oliveira.
 */
class FakeMoviesLocalDataSource : MovieLocalDataSource
{
    var movies: MutableList<Movie> = mutableListOf()

    override fun save(movie: Movie): Completable
    {
        movies.remove(movie)
        return Completable.complete()
    }

    override fun delete(movie: Movie): Completable
    {
        movies.remove(movie)
        return Completable.complete()
    }

    override fun isFavorite(movie: Movie): Single<Boolean>
    {
        return Single.just(movies.contains(movie))
    }

    override fun getMovies(): DataSource.Factory<Int, Movie>
    {
        return object : DataSource.Factory<Int, Movie>()
        {
            override fun create(): DataSource<Int, Movie>
            {
                return object : PageKeyedDataSource<Int, Movie>()
                {
                    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>)
                    {
                        callback.onResult(movies, 1, null)
                    }

                    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>)
                    {
                        callback.onResult(movies, null)
                    }

                    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>)
                    {
                        /* not implemented */
                    }
                }
            }
        }
    }
}