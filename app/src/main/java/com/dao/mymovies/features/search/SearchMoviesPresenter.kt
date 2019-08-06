package com.dao.mymovies.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dao.mymovies.data.MovieRepository
import com.dao.mymovies.features.search.paging.SearchDataSourceFactory
import com.dao.mymovies.model.Movie
import io.reactivex.disposables.CompositeDisposable

/**
 * Created in 06/08/18 10:43.
 *
 * @author Diogo Oliveira.
 */
class SearchMoviesPresenter(
        private val repository: MovieRepository,
        private val composite: CompositeDisposable) : SearchMoviesInteractor.Presenter
{
    private lateinit var view: SearchMoviesInteractor.View

    private val query = MutableLiveData<String>()
    private val search = map(query) { searchDataSourceFactory(it) }
    private val searchObserver: LiveData<PagedList<Movie>> = switchMap(search) { it }

    override fun initialize(view: SearchMoviesInteractor.View)
    {
        this.view = view
        this.view.initializeView()
    }

    override fun searchObserver(): LiveData<PagedList<Movie>>
    {
        return searchObserver
    }

    override fun searchMovies(query: String)
    {
        this.query.value = query
    }

    override fun terminate()
    {
        composite.clear()
    }

    private fun searchDataSourceFactory(query: String): LiveData<PagedList<Movie>>
    {
        val factory = SearchDataSourceFactory(query, composite, repository)
        val config = PagedList.Config.Builder().setPageSize(30).build()
        val paged = LivePagedListBuilder(factory, config).build()

        view.networkStateObserver(switchMap(factory.source) { it.networkState })
        return paged
    }
}