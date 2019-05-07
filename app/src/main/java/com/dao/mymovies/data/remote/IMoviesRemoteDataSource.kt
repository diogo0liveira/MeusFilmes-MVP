package com.dao.mymovies.data.remote

import com.dao.mymovies.network.TheMovies
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created in 03/08/18 13:15.
 *
 * @author Diogo Oliveira.
 */
@Singleton
class IMoviesRemoteDataSource @Inject constructor(private val service: TheMovies) : MovieRemoteDataSource
{
    override fun search(query: String, page: Int) = service.search(query, page)
}