package com.dao.mymovies.features.search.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
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
                    callback.onResult(response.body()?.results ?: emptyList(), search.page, search.totalPages)

                }
            }

            networkState.postValue(NetworkState.SUCCESS)
        }

        val error: Consumer<Throwable> = Consumer {
            networkState.postValue(NetworkState.error(it.message) { loadInitial(params, callback) })
        }

        val disposable = repository
                .search(query)
                .doOnSubscribe { networkState.postValue(NetworkState.RUNNING) }
                .compose(schedulerProvider.applySchedulers())
                .subscribe(consumer, error)

        composite.add(disposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>)
    {
        val consumer: Consumer<Response<SearchResult>> = Consumer { response ->
            callback.onResult(response.body()?.results ?: emptyList(), params.requestedLoadSize)
            networkState.postValue(NetworkState.SUCCESS)
        }

        val error: Consumer<Throwable> = Consumer {
            networkState.postValue(NetworkState.error(it.message) { loadAfter(params, callback) })
        }

        val disposable = repository
                .search(query, params.requestedLoadSize)
                .doOnSubscribe { networkState.postValue(NetworkState.RUNNING) }
                .compose(schedulerProvider.applySchedulers()).subscribe(consumer, error)

        composite.add(disposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>)
    {
        /* not implemented */
    }
}