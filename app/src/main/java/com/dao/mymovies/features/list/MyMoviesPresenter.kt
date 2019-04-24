package com.dao.mymovies.features.list

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dao.mymovies.data.repository.MoviesRepository
import com.dao.mymovies.model.Movie

/**
 * Created in 03/08/18 12:03.
 *
 * @author Diogo Oliveira.
 */
class MyMoviesPresenter (repository: MoviesRepository) : MyMoviesInteractor.Presenter
{
    private lateinit var view: MyMoviesInteractor.View
    private val movies: LiveData<PagedList<Movie>>

    init {
        val config = PagedList.Config.Builder()
                .setPageSize(30).build()

        movies = LivePagedListBuilder(repository.loadMovies(), config).build()

    }

    override fun initialize(view: MyMoviesInteractor.View)
    {
        this.view = view
        this.view.initializeView()
    }

    override fun moviesObserver(): LiveData<PagedList<Movie>> = movies
}