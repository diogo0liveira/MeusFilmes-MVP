package com.dao.mymovies.features.search.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.dao.mymovies.R
import com.dao.mymovies.data.repository.MoviesRepository
import com.dao.mymovies.model.Movie
import com.dao.mymovies.network.NetworkState
import com.dao.mymovies.pojo.SearchResult
import com.dao.mymovies.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import retrofit2.Response
import javax.inject.Inject

/**
 * Created in 02/04/19 11:34.
 *
 * @author Diogo Oliveira.
 */
class SearchPageKeyedDataSource @Inject constructor(
        private val query: String,
        private val composite: CompositeDisposable,
        private val schedulerProvider: SchedulerProvider,
        private val repository: MoviesRepository) : PageKeyedDataSource<Int, Movie>()
{
    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>)
    {
        val consumer: Consumer<Response<SearchResult>> = Consumer { response ->
            if(response.isSuccessful)
            {
                response.body()?.let { search ->
                    val limits = pagination(search)
                    callback.onResult(search.results.filter { filter(it) }, limits["prev"], limits["next"])
                }

                networkState.postValue(NetworkState.SUCCESS)
            }
            else
            {
                networkState.postValue(NetworkState.error(R.string.app_internal_no_connection) {
                    loadInitial(params, callback)
                })
            }
        }

        val error: Consumer<Throwable> = Consumer {
            networkState.postValue(NetworkState.error(it.message) { loadInitial(params, callback) })
        }

        val disposable = repository
                .search(query, 1)
                .doOnSubscribe { networkState.postValue(NetworkState.RUNNING) }
                .compose(schedulerProvider.applySchedulers())
                .subscribe(consumer, error)

        composite.add(disposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>)
    {
        val consumer: Consumer<Response<SearchResult>> = Consumer { response ->
            if(response.isSuccessful)
            {
                response.body()?.let { search ->
                    val limits = pagination(search)
                    callback.onResult(search.results.filter { filter(it) }, limits["next"])
                }

                networkState.postValue(NetworkState.SUCCESS)
            }
            else
            {
                networkState.postValue(NetworkState.error(R.string.app_internal_no_connection) {
                    loadAfter(params, callback)
                })
            }
        }

        val error: Consumer<Throwable> = Consumer {
            networkState.postValue(NetworkState.error(it.message) { loadAfter(params, callback) })
        }

        val disposable = repository.search(query, params.key)
                .doOnSubscribe { networkState.postValue(NetworkState.RUNNING) }
                .compose(schedulerProvider.applySchedulers()).subscribe(consumer, error)

        composite.add(disposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>)
    {
        /* not implemented */
    }

    private fun pagination(search: SearchResult): Map<String, Int?>
    {
        val next = if(search.totalPages > search.page) (search.page + 1) else null
        return mutableMapOf("prev" to search.page, "next" to next)
    }

    private fun filter(movie: Movie): Boolean
    {
        return (movie.title.isNotEmpty() && movie.title.isNotEmpty()) &&
               (movie.overView.isNotEmpty() && movie.overView.isNotBlank())
    }
}