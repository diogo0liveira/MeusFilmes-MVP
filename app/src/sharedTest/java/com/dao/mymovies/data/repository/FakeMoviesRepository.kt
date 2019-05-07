package com.dao.mymovies.data.repository

import androidx.paging.DataSource
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
class FakeMoviesRepository: MovieRepository
{
    var movies: List<Movie> = listOf()

    init
    {
        val movie = Movie()
    }

    override fun save(movie: Movie): Completable
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(movie: Movie): Completable
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isFavorite(movie: Movie): Single<Boolean>
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMovies(): DataSource.Factory<Int, Movie>
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun search(query: String, page: Int): Observable<Response<SearchResult>>
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}