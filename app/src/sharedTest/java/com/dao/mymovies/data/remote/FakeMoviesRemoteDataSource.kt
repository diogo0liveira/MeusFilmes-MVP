package com.dao.mymovies.data.remote

import com.dao.mymovies.model.Movie
import com.dao.mymovies.pojo.SearchResult
import io.reactivex.Observable
import retrofit2.Response

/**
 * Created in 07/05/19 10:37.
 *
 * @author Diogo Oliveira.
 */
class FakeMoviesRemoteDataSource : MovieRemoteDataSource
{
    var movies: MutableList<Movie> = mutableListOf()

    override fun search(query: String, page: Int): Observable<Response<SearchResult>>
    {
        val list = movies.filter { it.title.contains(query) }
        return Observable.just(Response.success(SearchResult(1, list.size, 1, list)))
    }
}