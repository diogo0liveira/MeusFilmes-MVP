package com.dao.mymovies.data.repository

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.dao.mymovies.data.MovieRepository
import com.dao.mymovies.model.Movie
import com.dao.mymovies.pojo.SearchResult
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response

/**
 * Created in 06/05/19 17:28.
 *
 * @author Diogo Oliveira.
 */
class FakeMoviesRepository : MovieRepository
{
    var movies: MutableList<Movie> = mutableListOf()

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
        return Single.just(movies.find { it.id == movie.id }?.isFavorite?.get() ?: movie.isFavorite.get())
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
                        TODO("not implemented")
                    }

                }
            }
        }
    }

    override fun search(query: String, page: Int): Observable<Response<SearchResult>>
    {
        val list = movies.filter { it.title.contains(query) }
        return Observable.just(Response.success(SearchResult(1, list.size, 1, list)))
    }
}