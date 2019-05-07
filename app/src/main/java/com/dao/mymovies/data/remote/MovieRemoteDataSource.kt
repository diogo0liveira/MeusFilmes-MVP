package com.dao.mymovies.data.remote

import com.dao.mymovies.pojo.SearchResult
import io.reactivex.Observable
import retrofit2.Response

/**
 * Created in 03/08/18 13:17.
 *
 * @author Diogo Oliveira.
 */
interface MovieRemoteDataSource
{
    fun search(query: String, page: Int = 1): Observable<Response<SearchResult>>
}