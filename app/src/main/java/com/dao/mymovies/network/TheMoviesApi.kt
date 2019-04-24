package com.dao.mymovies.network

import com.dao.mymovies.KeyParameter
import com.dao.mymovies.TheMovieApi
import com.dao.mymovies.pojo.SearchResult
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created in 24/04/19 10:29.
 *
 * @author Diogo Oliveira.
 */
interface TheMovies
{
    @GET(TheMovieApi.SEARCH)
    @Headers(ContentType.APPLICATION_JSON)
    fun search(@Query(KeyParameter.QUERY) query: String, @Query(KeyParameter.PAGE) page: Int): Observable<Response<SearchResult>>
}