package com.dao.mymovies.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dao.mymovies.base.mvp.Repository
import com.dao.mymovies.data.MovieDataSourceInteractor
import com.dao.mymovies.data.local.MoviesLocalDataSource
import com.dao.mymovies.data.remote.MoviesRemoteDataSource
import com.dao.mymovies.features.search.paging.SearchDataSourceFactory
import com.dao.mymovies.model.Movie
import com.dao.mymovies.pojo.SearchResult
import com.dao.mymovies.util.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Response
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created in 03/08/18 12:30.
 *
 * @author Diogo Oliveira.
 */
@Singleton
class MoviesRepository @Inject constructor(
        local: MoviesLocalDataSource,
        remote: MoviesRemoteDataSource,
        private val composite: CompositeDisposable,
        private val schedulerProvider: SchedulerProvider):
        Repository<MoviesLocalDataSource, MoviesRemoteDataSource>(local, remote), MovieDataSourceInteractor
{
    fun save(movie: Movie): Completable = local.save(movie)

    fun delete(movie: Movie) = local.delete(movie)

    fun loadMovies(): DataSource.Factory<Int, Movie> = local.search()

    fun isFavorite(movie: Movie): Flowable<Boolean> = local.isFavorite(movie)

    fun search1(query: String, page: Int): LiveData<PagedList<Movie>>
    {
        val factory = SearchDataSourceFactory(query, composite, schedulerProvider, this)

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(page).build()

        val paged = LivePagedListBuilder(factory, config)
                .setFetchExecutor(Executors.newFixedThreadPool(5))
                .build()

        val networkState = switchMap(factory.source) { it.networkState }

        return paged
    }

    override fun search(query: String, page: Int): Observable<Response<SearchResult>> = remote.search(query, page)
}