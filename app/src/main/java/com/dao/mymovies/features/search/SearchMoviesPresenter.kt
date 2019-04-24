package com.dao.mymovies.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.PagedList
import com.dao.mymovies.data.repository.MoviesRepository
import com.dao.mymovies.model.Movie
import io.reactivex.disposables.CompositeDisposable

/**
 * Created in 06/08/18 10:43.
 *
 * @author Diogo Oliveira.
 */
class SearchMoviesPresenter (repository: MoviesRepository) : SearchMoviesInteractor.Presenter
{
    private lateinit var view: SearchMoviesInteractor.View
    private val composite: CompositeDisposable = CompositeDisposable()

    private val query = MutableLiveData<String>()
    private val search = map(query) { repository.search1(it, 1) }
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
}