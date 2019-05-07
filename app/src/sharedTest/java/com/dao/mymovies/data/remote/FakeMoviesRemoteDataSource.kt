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
class FakeMoviesRemoteDataSource(private val movies: MutableList<Movie>) : MovieRemoteDataSource
{
    override fun search(query: String, page: Int): Observable<Response<SearchResult>>
    {
        val response: Response<SearchResult> = if(query.isEmpty())
        {
            Response.success(SearchResult(page, 0, 0, emptyList()))
        }
        else
        {
            Response.success(SearchResult(page, movies.size, (page + 1), movies))
        }

        return Observable.just(response)
    }
}