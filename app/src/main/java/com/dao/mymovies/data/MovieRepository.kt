package com.dao.mymovies.data

import androidx.paging.DataSource
import com.dao.mymovies.model.Movie
import com.dao.mymovies.pojo.SearchResult
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response

/**
 * Created in 07/05/19 10:04.
 *
 * @author Diogo Oliveira.
 */
interface MovieRepository
{
    fun save(movie: Movie): Completable

    fun delete(movie: Movie): Completable

    fun isFavorite(movie: Movie): Single<Boolean>

    fun getMovies(): DataSource.Factory<Int, Movie>

    fun search(query: String, page: Int = 1): Observable<Response<SearchResult>>
}